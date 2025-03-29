package uk.ac.tees.mad.qrscanner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import uk.ac.tees.mad.qrscanner.data.model.ScanFavorite
import uk.ac.tees.mad.qrscanner.data.model.ScanHistory
import uk.ac.tees.mad.qrscanner.data.repository.Repository
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val repository: Repository
): ViewModel() {

    private val _history = MutableStateFlow(emptyList<ScanHistory>())
    val history: StateFlow<List<ScanHistory>> get() = _history
    private val _favorite = MutableStateFlow(emptyList<ScanFavorite>())
    val favorite: StateFlow<List<ScanFavorite>> get() = _favorite
    init {
        syncData()
        fetchData()
    }

    private fun fetchData(){
        val user = auth.currentUser
        if (user!=null){
            viewModelScope.launch {
                repository.getHistory(user.uid).collect {
                    _history.value = it
                }
            }
            viewModelScope.launch {
                repository.getFavorite(user.uid).collect {
                    _favorite.value = it
                }
            }
        }
    }

    fun deleteFavorite(entity: ScanFavorite){
        val user = auth.currentUser!!
        db.collection("USERS")
            .document(user.uid)
            .collection("FAVORITE")
            .document(entity.id)
            .delete()
            .addOnSuccessListener {
                viewModelScope.launch {
                    repository.deleteFavorite(entity)
                }
            }
    }

    fun deleteHistory(entity: ScanHistory){
        viewModelScope.launch {
            repository.deleteOneHistory(entity)
        }
    }

    private fun syncData(){
        val user = auth.currentUser
        if (user!=null){
            viewModelScope.launch {
                val fav = repository.getFavorite(user.uid).first()
                if (fav.isEmpty()){
                    db.collection("USERS")
                        .document(user.uid)
                        .collection("FAVORITE")
                        .get()
                        .addOnSuccessListener { documents->
                            viewModelScope.launch {
                                documents.forEach { doc->
                                    val mFav = doc.toObject(ScanFavorite::class.java)
                                    repository.addFavorite(mFav)
                                }
                            }
                        }
                }
            }
        }
    }
}
package uk.ac.tees.mad.qrscanner.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import uk.ac.tees.mad.qrscanner.data.repository.Repository
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val repository: Repository
): ViewModel() {

    init {
        fetchHistory()
    }

    fun fetchHistory(){
        val user = auth.currentUser
        if (user!=null){
            viewModelScope.launch {
                val data = repository.getHistory(user.uid).first()
                Log.d("History Data", data.size.toString())
            }
        }
    }
}
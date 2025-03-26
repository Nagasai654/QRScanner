package uk.ac.tees.mad.qrscanner.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import uk.ac.tees.mad.qrscanner.data.model.ScanHistory
import uk.ac.tees.mad.qrscanner.data.repository.Repository
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val repository: Repository
): ViewModel() {

    private val _history = MutableStateFlow(emptyList<ScanHistory>())
    val history: StateFlow<List<ScanHistory>> get() = _history
    init {
        fetchHistory()
    }

    fun fetchHistory(){
        val user = auth.currentUser
        if (user!=null){
            viewModelScope.launch {
                repository.getHistory(user.uid).collect {
                    _history.value = it
                }
            }
        }
    }
}
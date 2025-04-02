package uk.ac.tees.mad.qrscanner.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.qrscanner.data.repository.Repository
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val repository: Repository
): ViewModel() {
    private val _user = MutableStateFlow<FirebaseUser?>(null)
    val user: StateFlow<FirebaseUser?> get() = _user

    init {
        fetchUser()
    }
    fun getName(): String{
        return auth.currentUser?.displayName?:""
    }

    fun getEmail(): String{
        return auth.currentUser?.email?:""
    }

    fun logOut(){
        auth.signOut()
        _user.value = null
    }

    fun clearHistory(context: Context){
        viewModelScope.launch {
            repository.deleteHistory(_user.value!!.uid)
            Toast.makeText(context,"History Cleared", Toast.LENGTH_SHORT).show()
        }
    }

    fun fetchUser(){
        _user.value = auth.currentUser
    }
}
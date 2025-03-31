package uk.ac.tees.mad.qrscanner.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import uk.ac.tees.mad.qrscanner.data.repository.Repository
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val repository: Repository
): ViewModel() {

    fun getName(): String{
        return auth.currentUser?.displayName?:""
    }

    fun getEmail(): String{
        return auth.currentUser?.email?:""
    }
}
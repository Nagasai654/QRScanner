package uk.ac.tees.mad.qrscanner.viewmodel

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth
): ViewModel() {

    fun createUser(
        name: String, email: String,
        password: String, confirmPassword: String,
        context: Context, onComplete:()-> Unit
        ){
        if (name.isEmpty()){
            Toast.makeText(context,"Name should not be empty", Toast.LENGTH_SHORT).show()
            return
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(context,"Email is invalid", Toast.LENGTH_SHORT).show()
            return
        }
        else if(password.isEmpty()){
            Toast.makeText(context,"Password should not be empty", Toast.LENGTH_SHORT).show()
            return
        }
        else if(password != confirmPassword){
            Toast.makeText(context,"Confirm password is different", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            val profileUpdate = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build()
            auth.currentUser?.updateProfile(profileUpdate)
            Toast.makeText(context, "User created successfully", Toast.LENGTH_SHORT).show()
            onComplete()
        }
        .addOnCompleteListener {
            Toast.makeText(context, "User created successfully", Toast.LENGTH_SHORT).show()
        }
    }

    fun loginUser(
        email: String, password: String,
        context: Context, onComplete:()-> Unit
        ){
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(context,"Email is invalid", Toast.LENGTH_SHORT).show()
            return
        }
        else if(password.isEmpty()){
            Toast.makeText(context,"Password should not be empty", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
            Toast.makeText(context, "User loggedIn successfully", Toast.LENGTH_SHORT).show()
            onComplete()
        }
    }
}
package com.example.fooddeliveryapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthViewModel : ViewModel(){
    lateinit var messageObs: MutableLiveData<String>
    var firebaseUserLiveData : MutableLiveData<FirebaseUser> = MutableLiveData()
    var userLoggedLiveData : MutableLiveData<Boolean> = MutableLiveData()
    private val auth = Firebase.auth
    private lateinit var currentUser: FirebaseUser



    fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if(auth.currentUser != null) {
                    currentUser = auth.currentUser as FirebaseUser
                    firebaseUserLiveData.postValue(auth.currentUser)
                }

            } else {
                messageObs = MutableLiveData<String>()
                messageObs.postValue(task.exception!!.message)
            }
            sendEmailVerification()
        }
    }
    private fun sendEmailVerification() {
        currentUser.let {
            currentUser.sendEmailVerification()
        }
    }


    fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firebaseUserLiveData.postValue(auth.currentUser)
                } else {
                    messageObs.postValue(task.exception!!.message)
                }
            }
    }

    fun signOut() {
        auth.signOut()
        userLoggedLiveData.postValue(true)
    }

    init {
        if (auth.currentUser != null) {
            firebaseUserLiveData.postValue(auth.currentUser)
        }
    }
}
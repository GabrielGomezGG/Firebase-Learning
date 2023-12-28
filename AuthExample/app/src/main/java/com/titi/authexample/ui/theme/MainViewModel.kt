package com.titi.authexample.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainViewModel : ViewModel(

) {

    private val firebaseAuth = FirebaseAuth.getInstance()

    fun signOut() {
        firebaseAuth.signOut()
    }

    fun logIn(email: String, password: String): FirebaseUser? {
        var user : FirebaseUser? = null
        viewModelScope.launch {
            user =  firebaseAuth.signInWithEmailAndPassword(email, password).await().user
        }
        return user
    }

    suspend fun createAccount(email: String, password: String): FirebaseUser? {
        return firebaseAuth.createUserWithEmailAndPassword(email, password).await().user
    }

}
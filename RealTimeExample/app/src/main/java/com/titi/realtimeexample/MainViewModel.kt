package com.titi.realtimeexample

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val firebaseInstance: FirebaseInstance
) : ViewModel() {

    private val _post = MutableStateFlow<List<Post>>(emptyList())
    val post = _post.asStateFlow()

    init {
        readDatabase()
    }

    fun writeOnFirebase() {
        firebaseInstance.writeOnFirebase()
    }

    fun readDatabase() {
        firebaseInstance.readDatabase{
            _post.value = it
        }
    }

    fun deleteOnFirebase(reference: String) {
        firebaseInstance.deleteOnFirebase(reference)
    }

}
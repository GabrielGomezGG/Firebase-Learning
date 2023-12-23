package com.titi.realtimeexample

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class FirebaseInstance @Inject constructor(
    private val databaseRef: DatabaseReference
) {

    fun writeOnFirebase() {
        databaseRef.push().setValue(
            Post(title = "titi", body = "tete")
        )
    }

    fun readDatabase(
        valueChanged: (List<Post>) -> Unit
    ) {

        var value: List<Post> = emptyList()

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                value = snapshot.children.map {
                    it.getValue(Post::class.java)!!
                }
                valueChanged(value)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        databaseRef.addValueEventListener(postListener)
    }
}

data class Post(
    val title: String? = null,
    val body: String? = null
)
package com.titi.realtimeexample

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.titi.realtimeexample.ui.theme.RealTimeExampleTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    private lateinit var firebaseInstance: FirebaseInstance
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RealTimeExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    firebaseInstance = FirebaseInstance(this)

                    var value by remember {
                        mutableStateOf("")
                    }

                     setupListeners(firebaseInstance){
                         value = it
                     }

                    MainScreen(
                        value = value,
                        onClick = firebaseInstance::writeOnFirebase
                    )
                }
            }
        }
    }
}

fun setupListeners(
    firebaseInstance: FirebaseInstance,
    onValueChange: (String) -> Unit
){

//    var value : String? = null

    val postListener = object : ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
//            value = snapshot.getValue<String>()
            onValueChange(snapshot.getValue<String>() ?: "")
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }
    }
    firebaseInstance.setUpListener(postListener)
}

@Composable
fun MainScreen(
    value: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center)
    {
        Column {
            Button(onClick = { onClick() }) {
                Text(text = "Actualizar")
            }
            Text(text = value)
        }
    }
}

class FirebaseInstance(context: Context){

    private val database = Firebase.database
    private val databaseRef = database.reference
    init {
        FirebaseApp.initializeApp(context)
    }

    fun writeOnFirebase(){
        val randomValue = Random.nextInt(100).toString()
        databaseRef.setValue("Mi primera escritura $randomValue")
    }

    fun setUpListener(postListener: ValueEventListener) {
        databaseRef.addValueEventListener(postListener)
    }
}
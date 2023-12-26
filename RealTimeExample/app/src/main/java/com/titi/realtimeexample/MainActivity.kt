package com.titi.realtimeexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.titi.realtimeexample.ui.theme.RealTimeExampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RealTimeExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val posts by mainViewModel.post.collectAsState()

                    MainScreen(
                        value = posts,
                        onClick = mainViewModel::writeOnFirebase,
                        onClickText = {
                            mainViewModel.deleteOnFirebase(it)
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun MainScreen(
    value: List<Post>,
    onClick: () -> Unit,
    onClickText: (String) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    )
    {
        LazyColumn() {
            item {
                Button(onClick = { onClick() }) {
                    Text(text = "Actualizar")
                }
            }
            items(value) {
                ListItem(
                    headlineContent = { Text(it.title!!) },
                    supportingContent = { Text(it.body!!) },
                    trailingContent = {
                        IconButton(onClick = { onClickText(it.id!!) }) {
                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = "Localized description",
                            )
                        }
                    },
                )
//                ListItem(
//                    headlineContent = { Text("One line list item with 24x24 icon") },
//                    leadingContent = {
//                        Icon(
//                            Icons.Filled.Favorite,
//                            contentDescription = "Localized description",
//                        )
//                    }
//                )
//                Text(
//                    text = it.title!!,
//                    Modifier.clickable {
//                        onClickText(it.id!!)
//                    }
//                )
            }
        }
    }
}
package com.titi.storageexample.ui

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import java.io.File
import java.util.Objects
import java.util.UUID

@Composable
fun CameraScreen(
    context: Context,
    mainViewModel: MainViewModel = hiltViewModel()
) {

    var uri: Uri? by remember {
        mutableStateOf(null)
    }

    val camera = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = {
            if (it && uri?.path?.isNotEmpty() == true) {
                // do something with the uri
                mainViewModel.uploadBasicImage(uri!!)
            }
        }
    )
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    )
    {
        Button(onClick = {
            uri = generateUri(context)
            camera.launch(uri)
        }) {
            Text(text = "Camera")
        }
    }
}

fun generateUri(
    context: Context
): Uri {
    return FileProvider.getUriForFile(
        Objects.requireNonNull(
            context
        ),
        "com.titi.storageexample.provider",
        File.createTempFile(UUID.randomUUID().toString(), ".jpg", context.externalCacheDir)
    )
}
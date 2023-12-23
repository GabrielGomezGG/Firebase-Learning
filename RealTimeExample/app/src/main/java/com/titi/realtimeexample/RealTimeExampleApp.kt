package com.titi.realtimeexample

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RealTimeExampleApp : Application(){

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}
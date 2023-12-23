package com.titi.realtimeexample

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModelu {

    @Singleton
    @Provides
    fun provideFirebaseDataBase() = Firebase.database

    @Singleton
    @Provides
    fun provideFirebaseDataBaseReference(
        database: FirebaseDatabase
    ) = database.reference

    @Singleton
    @Provides
    fun provideFirebaseInstance(
        databaseRef: DatabaseReference
    ) = FirebaseInstance(databaseRef)

}
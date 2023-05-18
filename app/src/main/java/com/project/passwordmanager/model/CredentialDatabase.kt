package com.project.passwordmanager.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Credential::class], version = 1, exportSchema = false)
abstract class CredentialDatabase : RoomDatabase(){
    abstract val credentialDao: CredentialDao

    companion object{
        @Volatile
        private var INSTANCE: CredentialDatabase? = null

        fun getInstance(context: Context) : CredentialDatabase {  //returns an instance of RecordsDatabase. It builds the database if it doesn't exit yet
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CredentialDatabase::class.java,
                        "credentials_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
package com.project.passwordmanager.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * This abstract class serves as the Room database class for the Credential entity.
 * It provides methods to access the DAO and create the database instance.
 */
@Database(entities = [Credential::class], version = 1, exportSchema = false)
@TypeConverters(LocalDateConverter::class)
abstract class CredentialDatabase : RoomDatabase() {

    /**
     * Retrieves the CredentialDao interface to access the database operations for Credential entity.
     */
    abstract val credentialDao: CredentialDao

    companion object {
        @Volatile
        private var INSTANCE: CredentialDatabase? = null

        /**
         * Returns an instance of CredentialDatabase.
         * It builds the database if it doesn't exist yet.
         *
         * @param context The application context.
         * @return An instance of CredentialDatabase.
         */
        fun getInstance(context: Context): CredentialDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CredentialDatabase::class.java,
                        "credentials_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}

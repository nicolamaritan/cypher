package com.project.passwordmanager.common

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.project.passwordmanager.security.Hashing

/**
 * Utility class that provides various helper methods for common operations.
 */
object Utils
{
    /**
     * Retrieves the hashed master password from the shared preferences.
     *
     * @param context The context of the application.
     * @return The hashed master password, or an empty string if it is not found.
     */
    fun getHashedMasterPassword(context: Context) : String
    {
        return context.getSharedPreferences(Constants.SYSTEM_PREFERENCES, AppCompatActivity.MODE_PRIVATE)
            .getString(Constants.MASTER_PASSWORD, "")!!
    }

    /**
     * Sets the hashed master password in the shared preferences.
     *
     * @param context The context of the application.
     * @param clearMasterPassword The clear master password to be hashed and stored.
     */
    fun setHashedMasterPassword(context: Context, clearMasterPassword: String)
    {
        context.getSharedPreferences(Constants.SYSTEM_PREFERENCES, AppCompatActivity.MODE_PRIVATE)
            .edit()
            .putString(Constants.MASTER_PASSWORD, Hashing.sha256(clearMasterPassword))
            .apply()
    }
}
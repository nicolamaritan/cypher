package com.project.passwordmanager.common

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.project.passwordmanager.security.Hashing

object Utils
{
    fun getHashedMasterPassword(context: Context)
    {
        context.getSharedPreferences(Constants.SYSTEM_PREFERENCES, AppCompatActivity.MODE_PRIVATE)
            .getString(Constants.MASTER_PASSWORD, "")
    }

    fun setHashedMasterPassword(context: Context, clearMasterPassword: String)
    {
        context.getSharedPreferences(Constants.SYSTEM_PREFERENCES, AppCompatActivity.MODE_PRIVATE)
            .edit()
            .putString(Constants.MASTER_PASSWORD, Hashing.sha256(clearMasterPassword))
            .apply()
    }
}
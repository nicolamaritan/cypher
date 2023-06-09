package com.project.passwordmanager.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.passwordmanager.model.CredentialDao
import com.project.passwordmanager.security.Cryptography
import kotlinx.coroutines.launch

class SettingsViewModel(val dao: CredentialDao) : ViewModel()
{
    fun updatePasswords(old : String, newpw : String){
        viewModelScope.launch{
            val credentials = dao.getAllAsync()
            for (credential in credentials) {
                Log.d(TAG, credential.toString())
                val decryptedPassword = Cryptography.decryptText(credential.password, old)
                val encryptedPassword = Cryptography.encryptText(decryptedPassword, newpw)
                credential.password = encryptedPassword
                dao.update(credential)
            }
        }
    }
    companion object
    {
        private val TAG = Companion::class.java.name
    }
}
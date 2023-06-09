package com.project.passwordmanager.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.passwordmanager.model.CredentialDao
import com.project.passwordmanager.security.Cryptography
import kotlinx.coroutines.launch

class SettingsViewModel(val dao: CredentialDao) : ViewModel()
{
    /**
     * Updates the passwords of all credentials in the database.
     *
     * This method asynchronously retrieves all credentials from the database using the provided [dao].
     * It then iterates over each credential, decrypts the password using the [oldMasterPassword] password,
     * encrypts it using the [newMasterPassword] password, and updates the credential in the database.
     *
     * @param oldMasterPassword The old password used for decrypting the stored passwords.
     * @param newMasterPassword The new password used for encrypting the passwords.
     */
    fun updatePasswords(oldMasterPassword : String, newMasterPassword : String){
        viewModelScope.launch{
            val credentials = dao.getAllAsync()
            for (credential in credentials) {
                Log.d(TAG, credential.toString())
                val decryptedPassword = Cryptography.decryptText(credential.password, oldMasterPassword)
                val encryptedPassword = Cryptography.encryptText(decryptedPassword, newMasterPassword)
                credential.password = encryptedPassword
                dao.update(credential)
            }
        }
    }
    companion object
    {
        private val TAG = SettingsViewModel::class.java.simpleName
    }
}
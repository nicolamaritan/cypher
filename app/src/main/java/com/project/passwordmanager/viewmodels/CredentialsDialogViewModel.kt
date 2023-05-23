package com.project.passwordmanager.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.passwordmanager.model.Credential
import com.project.passwordmanager.model.CredentialDao
import kotlinx.coroutines.launch

class CredentialsDialogViewModel(val dao: CredentialDao) : ViewModel()
{
    var newCredentialUsername = ""
    var newCredentialService = ""
    var newCredentialPassword = ""

    fun addCredential()
    {
        //TODO Add data verification system
        Log.d(TAG, "addCredential invoked.")
        viewModelScope.launch {
            val credential = Credential()
            credential.username = newCredentialUsername
            credential.service = newCredentialService
            credential.password = newCredentialPassword

            dao.insert(credential)
        }

    }

    companion object
    {
        val TAG = CredentialsDialogViewModel::javaClass.toString()
    }
}
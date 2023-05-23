package com.project.passwordmanager.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.passwordmanager.common.CredentialValidator
import com.project.passwordmanager.model.Credential
import com.project.passwordmanager.model.CredentialDao
import kotlinx.coroutines.launch

class CredentialsDialogViewModel(val dao: CredentialDao) : ViewModel()
{
    var newCredentialUsername = ""
    var newCredentialService = ""
    var newCredentialPassword = ""
    var closing = false

    fun addCredential()
    {
        Log.d(TAG, "addCredential invoked.")
        val credential = Credential()
        credential.username = newCredentialUsername
        credential.service = newCredentialService
        credential.password = newCredentialPassword

        if (!CredentialValidator.validate(credential))
        {
            // TODO add display message
            return
        }

        viewModelScope.launch {
            dao.insert(credential)
        }

        closing = true
        //TODO add display message

    }

    companion object
    {
        val TAG = CredentialsDialogViewModel::javaClass.toString()
    }
}
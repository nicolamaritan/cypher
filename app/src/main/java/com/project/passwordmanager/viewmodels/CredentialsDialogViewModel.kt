package com.project.passwordmanager.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.passwordmanager.model.Credential
import com.project.passwordmanager.model.CredentialDao
import kotlinx.coroutines.launch

class CredentialsDialogViewModel(val dao: CredentialDao) : ViewModel()
{
    var newCredentialPassword = ""

    fun addCredential()
    {
        viewModelScope.launch {
            val credential = Credential()
            credential.password = newCredentialPassword
            dao.insert(credential)
        }

    }
}
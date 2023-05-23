package com.project.passwordmanager.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.passwordmanager.common.CredentialValidator
import com.project.passwordmanager.common.Event
import com.project.passwordmanager.model.Credential
import com.project.passwordmanager.model.CredentialDao
import kotlinx.coroutines.launch

class CredentialsDialogViewModel(val dao: CredentialDao) : ViewModel()
{
    var newCredentialUsername = ""
    var newCredentialService = ""
    var newCredentialPassword = ""
    var closing = false

    private val _message = MutableLiveData<Event<String>>()
    val message : LiveData<Event<String>>
        get() = _message

    fun addCredential()
    {
        Log.d(TAG, "addCredential invoked.")
        val credential = Credential()
        credential.username = newCredentialUsername
        credential.service = newCredentialService
        credential.password = newCredentialPassword

        if (!CredentialValidator.validate(credential))
        {
            _message.value = Event("Invalid input data")
            return
        }

        viewModelScope.launch {
            dao.insert(credential)
        }

        closing = true
        _message.value = Event("Credential added :)")

    }

    companion object
    {
        val TAG = CredentialsDialogViewModel::javaClass.toString()
    }
}
package com.project.passwordmanager.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.passwordmanager.model.CredentialDao
import com.project.passwordmanager.viewmodels.CredentialsDialogViewModel
import com.project.passwordmanager.viewmodels.CredentialsViewModel

class CredentialsDialogViewModelFactory(private val dao: CredentialDao)
    : ViewModelProvider.Factory
{
    /*
        Suppresses the warning as the method checks if the modelClass
        is castable to a CredentialsDialogViewModel. If it is not,
        the method throws IllegalArgumentException.
    */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if (modelClass.isAssignableFrom(CredentialsDialogViewModel::class.java))
        {
            return CredentialsDialogViewModel(dao) as T
        }
        throw IllegalArgumentException("Wrong ViewModel")
    }
}
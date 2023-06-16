package com.project.passwordmanager.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.passwordmanager.model.CredentialDao
import com.project.passwordmanager.viewmodels.AddCredentialActivityViewModel

/**
 * This factory class is responsible for creating instances of the AddCredentialDialog.
 * It implements the ViewModelProvider.Factory interface.
 */
class AddCredentialActivityViewModelFactory(private val dao: CredentialDao)
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
        if (modelClass.isAssignableFrom(AddCredentialActivityViewModel::class.java))
        {
            return AddCredentialActivityViewModel(dao) as T
        }
        throw IllegalArgumentException("Wrong ViewModel")
    }
}
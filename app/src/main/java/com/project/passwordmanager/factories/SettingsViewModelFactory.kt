package com.project.passwordmanager.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.passwordmanager.model.CredentialDao
import com.project.passwordmanager.viewmodels.SettingsViewModel

/**
 * This factory class is responsible for creating instances of the SettingsViewModel.
 * It implements the ViewModelProvider.Factory interface.
 */
class SettingsViewModelFactory(val dao : CredentialDao) : ViewModelProvider.Factory
{
    /*
        Suppresses the warning as the method checks if the modelClass
        is castable to a UnlockDialogViewModel. If it is not,
        the method throws IllegalArgumentException.
    */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
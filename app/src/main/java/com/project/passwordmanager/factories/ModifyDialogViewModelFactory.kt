package com.project.passwordmanager.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.passwordmanager.model.CredentialDao
import com.project.passwordmanager.viewmodels.ModifyDialogViewModel

class ModifyDialogViewModelFactory (private val dao: CredentialDao)
    : ViewModelProvider.Factory
{
    /*
        Suppresses the warning as the method checks if the modelClass
        is castable to a ModifyDialogViewModel. If it is not,
        the method throws IllegalArgumentException.
    */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if (modelClass.isAssignableFrom(ModifyDialogViewModel::class.java))
        {
            return ModifyDialogViewModel(dao) as T
        }
        throw IllegalArgumentException("Wrong ViewModel")
    }
}
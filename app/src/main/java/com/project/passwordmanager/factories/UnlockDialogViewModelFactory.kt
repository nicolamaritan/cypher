package com.project.passwordmanager.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.passwordmanager.viewmodels.UnlockDialogViewModel

class UnlockDialogViewModelFactory()
    : ViewModelProvider.Factory
{
    /*
        Suppresses the warning as the method checks if the modelClass
        is castable to a UnlockDialogViewModel. If it is not,
        the method throws IllegalArgumentException.
    */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if (modelClass.isAssignableFrom(UnlockDialogViewModel::class.java))
        {
            return UnlockDialogViewModel() as T
        }
        throw IllegalArgumentException("Wrong ViewModel")
    }
}
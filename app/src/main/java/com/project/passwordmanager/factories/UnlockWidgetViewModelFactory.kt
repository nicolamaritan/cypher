package com.project.passwordmanager.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.passwordmanager.viewmodels.UnlockWidgetViewModel

/**
 * This factory class is responsible for creating instances of the UnlockWidgetViewModel.
 * It implements the ViewModelProvider.Factory interface.
 */
class UnlockWidgetViewModelFactory(private var displayedPassword: String, private var displayedButtonText: String)
    : ViewModelProvider.Factory {
    /*
        Suppresses the warning as the method checks if the modelClass
        is castable to a UnlockDialogViewModel. If it is not,
        the method throws IllegalArgumentException.
    */
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if (modelClass.isAssignableFrom(UnlockWidgetViewModel::class.java))
        {
            @Suppress("UNCHECKED_CAST")
            return UnlockWidgetViewModel(displayedPassword, displayedButtonText) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

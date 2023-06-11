package com.project.passwordmanager.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.passwordmanager.viewmodels.UnlockWidgetViewModel

class UnlockWidgetViewModelFactory(private var displayedPassword: String, private var displayedButtonText: String)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UnlockWidgetViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UnlockWidgetViewModel(displayedPassword, displayedButtonText) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

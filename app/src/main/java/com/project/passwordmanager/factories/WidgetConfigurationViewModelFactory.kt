package com.project.passwordmanager.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.passwordmanager.model.CredentialDao
import com.project.passwordmanager.viewmodels.WidgetConfigurationViewModel

class WidgetConfigurationViewModelFactory(private val dao: CredentialDao)
    :ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WidgetConfigurationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WidgetConfigurationViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

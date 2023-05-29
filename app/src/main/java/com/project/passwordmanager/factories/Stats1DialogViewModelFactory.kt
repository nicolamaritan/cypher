package com.project.passwordmanager.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.passwordmanager.viewmodels.Stats1DialogViewModel

class Stats1DialogViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(Stats1DialogViewModel::class.java)) {
            return Stats1DialogViewModel() as T
        }
        throw IllegalArgumentException("Wrong ViewModel")
    }
}
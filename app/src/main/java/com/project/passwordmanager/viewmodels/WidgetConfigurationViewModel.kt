package com.project.passwordmanager.viewmodels

import androidx.lifecycle.ViewModel
import com.project.passwordmanager.model.CredentialDao

class WidgetConfigurationViewModel(dao: CredentialDao) : ViewModel()
{
    val credentials = dao.getAll()
}
package com.project.passwordmanager.viewmodels

import androidx.lifecycle.ViewModel
import com.project.passwordmanager.model.CredentialDao

class StatsViewModel(val dao: CredentialDao) : ViewModel()
{
    val credentials = dao.getSortedByDate()

    companion object
    {
        private val TAG = StatsViewModel::javaClass.toString()
    }
}
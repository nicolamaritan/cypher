package com.project.passwordmanager.viewmodels

import androidx.lifecycle.ViewModel
import com.project.passwordmanager.model.CredentialDao
import java.time.LocalDate

class StatsViewModel(val dao: CredentialDao) : ViewModel()
{
    val credentials = dao.getSortedByDate()
    val oldCredentialsCount = dao.getEntriesOlderThanSixMonthsCount(LocalDate.now())

    companion object
    {
        private val TAG = StatsViewModel::javaClass.toString()
    }
}
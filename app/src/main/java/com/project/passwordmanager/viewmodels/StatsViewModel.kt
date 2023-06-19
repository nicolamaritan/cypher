package com.project.passwordmanager.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.project.passwordmanager.model.CredentialDao
import java.time.LocalDate

/**
 * This ViewModel class represents the statistics screen logic and data management.
 * It is responsible for providing data to the UI, showing the last modified date
 * for each credential and other statistics.
 *
 * @param dao The `CredentialDao` used for data retrieval and manipulation.
 */
class StatsViewModel(val dao: CredentialDao) : ViewModel()
{
    init
    {
        Log.d(TAG, "StatsViewModel initialized")
    }

    val credentials = dao.getSortedByDate()
    val oldCredentialsCount = dao.getEntriesOlderThanSixMonthsCount(LocalDate.now().minusMonths(6))

    companion object
    {
        private val TAG = StatsViewModel::class.java.simpleName
    }
}
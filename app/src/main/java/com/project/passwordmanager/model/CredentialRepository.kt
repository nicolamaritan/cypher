package com.project.passwordmanager.model

import androidx.lifecycle.LiveData

/**
 * The repository class for managing Credential data. Data accessed from
 * here is primarily fetched from the widgets.
 *
 * @param credentialDao The CredentialDao used for data retrieval and manipulation.
 */
class CredentialRepository(credentialDao: CredentialDao)
{
    /**
     * LiveData object that holds a list of all credentials.
     */
    val allCredentials: LiveData<List<Credential>> = credentialDao.getAll()

    /**
     * LiveData object that holds a list of all credentials sorted by date.
     */
    val allCredentialSortedByDate: LiveData<List<Credential>> = credentialDao.getSortedByDate()
}

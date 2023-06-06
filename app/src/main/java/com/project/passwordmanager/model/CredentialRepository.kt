package com.project.passwordmanager.model

import androidx.lifecycle.LiveData

class CredentialRepository(credentialDao: CredentialDao)
{

    val allCredentials: LiveData<List<Credential>> = credentialDao.getAll()
    val allCredentialSortedByDate: LiveData<List<Credential>> = credentialDao.getSortedByDate()
}

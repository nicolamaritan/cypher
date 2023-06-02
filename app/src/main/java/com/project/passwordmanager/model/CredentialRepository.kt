package com.project.passwordmanager.model

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class CredentialRepository(private val credentialDao: CredentialDao) {

    val allCredentials: LiveData<List<Credential>> = credentialDao.getAll()

    // You must call this on a non-UI thread or your app will crash. So we're making this a
    // suspend function so the caller methods know this.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: Credential) {
        credentialDao.insert(word)
    }
}

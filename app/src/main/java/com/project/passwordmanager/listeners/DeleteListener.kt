package com.project.passwordmanager.listeners

/**
 * Interface for listening to delete events in the CredentialsFragment.
 */
interface DeleteListener
{
    /**
     * Called when a credential is removed.
     */
    fun onDeleteCredential(credentialId: Int)
}
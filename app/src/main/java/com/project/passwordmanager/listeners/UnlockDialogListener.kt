package com.project.passwordmanager.listeners

/**
 * Interface for listening to unlock events in the UnlockDialogFragment.
 */
interface UnlockDialogListener {
    /**
     * Called when the password is successfully unlocked.
     */
    fun onUnlockSuccess()

    /**
     * Called when the entered password is incorrect.
     */
    fun onUnlockFailure()
}
package com.project.passwordmanager.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.passwordmanager.R
import com.project.passwordmanager.security.Hashing

/**
 * UnlockDialogViewModel is a ViewModel class that handles the unlocking logic for the app.
 */
class UnlockDialogViewModel () : ViewModel() {
    /**
     * Indicates whether the dialog is closing.
     */
    var closing = false
    /**
     * Indicates if the password inserted is correct.
     */

    /**
     * The UnlockDialogListener instance to listen for unlock events.
     * It is initially set to null until a listener is assigned.
     */
    private var unlockDialogListener: UnlockDialogListener? = null

    /**
     * Sets the UnlockDialogListener to listen for unlock events.
     *
     * @param listener The UnlockDialogListener instance to set.
     */
    fun setUnlockDialogListener(listener: UnlockDialogListener) {
        unlockDialogListener = listener
    }

    private val _toastStringId = MutableLiveData<Int>()

    /**
     * LiveData for observing toast events.
     */
    val toastStringId: LiveData<Int>
        get() = _toastStringId

    /**
     * Attempts to unlock the app's behavior by comparing the hashed inserted password with the hashed master password.
     *
     * @param insertedPassword The password entered by the user.
     * @return True if the password is correct and the app is unlocked, false otherwise.
     */
    fun unlock(insertedPassword: String): Boolean {
        val hashedInsertedPassword = Hashing.sha256(insertedPassword)

        if (hashedInsertedPassword == Hashing.sha256("MASTER")) {
            unlockDialogListener?.onUnlockSuccess()
            closing = true
            _toastStringId.value = R.string.unlock_toast
            return true
        }
        unlockDialogListener?.onUnlockFailure()
        return false
    }

    companion object {

        /**
         * TAG used for logging.
         */
        val TAG: String = UnlockDialogViewModel::class.java.simpleName
    }
}
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
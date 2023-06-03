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


    private val _toastStringId = MutableLiveData<Int>()
    val toastStringId: LiveData<Int>
        get() = _toastStringId

    /**
     * Attempts to unlock the app's behavior by comparing the hashed inserted password with the hashed master password.
     *
     * @param insertedMasterPassword The password entered by the user.
     * @return True if the password is correct and the app is unlocked, false otherwise.
     */
    fun unlock(insertedMasterPassword: String, trueHashedMasterPassword: String): Boolean
    {
        val hashedInsertedPassword = Hashing.sha256(insertedMasterPassword)
        if (hashedInsertedPassword == trueHashedMasterPassword)
        {
            closing = true
            _toastStringId.value = R.string.unlock_toast
            return true
        }

        _toastStringId.value = R.string.wrong_master_password
        return false
    }

    companion object
    {
        val TAG: String = UnlockDialogViewModel::class.java.simpleName
    }
}

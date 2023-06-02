package com.project.passwordmanager.viewmodels

import android.appwidget.AppWidgetManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.passwordmanager.R
import com.project.passwordmanager.security.Hashing

class UnlockWidgetViewModel : ViewModel() {

    var appWidgetId: Int = AppWidgetManager.INVALID_APPWIDGET_ID

    private var _toastStringId = MutableLiveData<Int>()
    val toastStringId: LiveData<Int>
        get() = _toastStringId


    /**
     * Unlocks the password manager widget by comparing the hashed true master password with the inserted password.
     *
     * @param hashedTrueMasterPassword The hashed true master password.
     * @param insertedPassword The inserted password.
     * @return `true` if the passwords match, `false` otherwise.
     */
    fun unlock(hashedTrueMasterPassword: String, insertedPassword: String): Boolean {
        val hashedInsertedPassword = Hashing.sha256(insertedPassword)
        if (hashedInsertedPassword == hashedTrueMasterPassword)
        {
            _toastStringId.value = R.string.credential_unlocked_successfully
            return true
        }

        _toastStringId.value = R.string.wrong_master_password
        return false
    }
}

package com.project.passwordmanager.viewmodels

import android.appwidget.AppWidgetManager
import androidx.lifecycle.ViewModel
import com.project.passwordmanager.security.Hashing

/**
 * UnlockWidgetViewModel is a ViewModel class that handles the unlocking logic for the widget.
 *
 * @property appWidgetId The ID of the app widget to be unlocked.
 */
class UnlockWidgetViewModel : ViewModel() {

    var appWidgetId: Int = AppWidgetManager.INVALID_APPWIDGET_ID

    /**
     * Attempts to unlock the widget by comparing the hashed inserted password with the hashed master password.
     *
     * @param insertedPassword The password entered by the user.
     * @return True if the password is correct and the widget is unlocked, false otherwise.
     */
    fun unlock(insertedPassword: String): Boolean {
        val hashedInsertedPassword = Hashing.sha256(insertedPassword)
        return  (hashedInsertedPassword == Hashing.sha256("MASTER"))
    }
}

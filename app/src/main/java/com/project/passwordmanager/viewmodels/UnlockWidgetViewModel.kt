package com.project.passwordmanager.viewmodels

import android.appwidget.AppWidgetManager
import androidx.lifecycle.ViewModel
import com.project.passwordmanager.model.WidgetData
import com.project.passwordmanager.security.Hashing

class UnlockWidgetViewModel : ViewModel()
{
    var appWidgetId: Int = AppWidgetManager.INVALID_APPWIDGET_ID

    fun unlock(insertedPassword: String) : Boolean
    {
        val hashedInsertedPassword = Hashing.sha256(insertedPassword)

        if (hashedInsertedPassword == Hashing.sha256("MASTER"))
        {
            WidgetData.getWidgetData(appWidgetId).unlock(insertedPassword)
            return true
        }
        return false
    }
}
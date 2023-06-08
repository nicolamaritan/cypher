package com.project.passwordmanager.viewmodels

import android.appwidget.AppWidgetManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UnlockWidgetViewModel(var displayedPassword: String, var displayedButtonText: String) : ViewModel()
{
    var locked = true
    var appWidgetId: Int = AppWidgetManager.INVALID_APPWIDGET_ID

    private var _toastStringId = MutableLiveData<Int>()
    val toastStringId: LiveData<Int>
        get() = _toastStringId
}

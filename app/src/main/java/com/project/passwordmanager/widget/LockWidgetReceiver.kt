package com.project.passwordmanager.widget

import android.appwidget.AppWidgetManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.project.passwordmanager.model.WidgetData


class LockWidgetReceiver : BroadcastReceiver()
{
    override fun onReceive(context: Context?, intent: Intent?)
    {
        val appWidgetId: Int = intent!!.extras!!.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID)
        val widgetData = WidgetData.getWidgetData(appWidgetId)

        Toast.makeText(
            context,
            "Widget safely locked.",
            Toast.LENGTH_SHORT
        ).show()

        widgetData.lock()

        PasswordManagerWidget.updateAppWidget(context!!, appWidgetId)
    }
}
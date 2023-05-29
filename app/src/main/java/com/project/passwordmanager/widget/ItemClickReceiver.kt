package com.project.passwordmanager.widget

import android.appwidget.AppWidgetManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.project.passwordmanager.R
import com.project.passwordmanager.model.WidgetData

class ItemClickReceiver : BroadcastReceiver()
{
    override fun onReceive(context: Context?, intent: Intent?)
    {
        val appWidgetId: Int = intent!!.extras!!.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID)
        val position = intent.extras!!.getInt(PasswordManagerWidget.ITEM_POSITION)
        val widgetData = WidgetData.getWidgetData(appWidgetId)

        if (widgetData.locked)
        {
            Toast.makeText(
                context,
                "The widget is locked. Unlock the widget first.",
                Toast.LENGTH_SHORT).show()
        }
        else
        {
            Log.d(TAG, "Widget ID: $appWidgetId")
            Log.d(TAG, "List Item: $position" )

            widgetData[position].visible = !widgetData[position].visible

            // Notify Service
            val appWidgetManager = AppWidgetManager.getInstance(context)
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_listview)
        }

    }

    companion object
    {
        val TAG = ItemClickReceiver::class.java.toString()
    }
}
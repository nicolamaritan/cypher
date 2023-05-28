package com.project.passwordmanager.widget

import android.appwidget.AppWidgetManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.project.passwordmanager.R
import com.project.passwordmanager.model.WidgetData

class ItemClickReceiver : BroadcastReceiver()
{
    override fun onReceive(context: Context?, intent: Intent?)
    {
        val appWidgetId: Int = intent!!.extras!!.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID)
        val position = intent.extras!!.getInt(PasswordManagerWidget.ITEM_POSITION)

        /*if (!WidgetData.getWidgetData(appWidgetId).authenticated)
        {
            Toast.makeText(
                context,
                "You are not authenticated.",
                Toast.LENGTH_LONG).show()
        }*/

        Log.d(TAG, "Widget ID: $appWidgetId")
        Log.d(TAG, "List Item: $position" )


        if (WidgetData.getWidgetData(appWidgetId).isVisible(position))
        {
            WidgetData.getWidgetData(appWidgetId).hideEntry(position)
        }
        else
        {
            WidgetData.getWidgetData(appWidgetId).showEntry(position)
        }

        // Notify Service
        val appWidgetManager = AppWidgetManager.getInstance(context)
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_listview)
    }

    companion object
    {
        val TAG = ItemClickReceiver::class.java.toString()
    }
}
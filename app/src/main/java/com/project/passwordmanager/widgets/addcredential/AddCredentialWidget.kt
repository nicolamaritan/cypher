package com.project.passwordmanager.widgets.addcredential

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.project.passwordmanager.R
import com.project.passwordmanager.activities.AddCredentialActivity

/**
 * Implementation of App Widget functionality.
 */
class AddCredentialWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        // Create an Intent to launch ExampleActivity.
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            /* context = */ context,
            /* requestCode = */  0,
            /* intent = */ Intent(context, AddCredentialActivity::class.java),
            /* flags = */ PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Get the layout for the widget and attach an on-click listener
        // to the button.
        val views: RemoteViews = RemoteViews(
            context.packageName,
            R.layout.add_credential_widget
        ).apply {
            setOnClickPendingIntent(R.id.add_widget_button, pendingIntent)
        }
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}

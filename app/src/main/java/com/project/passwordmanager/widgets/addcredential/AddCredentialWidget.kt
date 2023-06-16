package com.project.passwordmanager.widgets.addcredential

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.SizeF
import android.widget.RemoteViews
import com.project.passwordmanager.R
import com.project.passwordmanager.activities.AddCredentialActivity

/**
 * Add widget. It allows the user to add a new credential from the
 * home screen. It has a single layout which works as button.
 */
class AddCredentialWidget : AppWidgetProvider()
{
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds)
        {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {}

    override fun onDisabled(context: Context) {}

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        // Create an Intent to launch ExampleActivity.
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            Intent(context, AddCredentialActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Get the layout for the widget and attach an on-click listener to the layout.
        val smallView: RemoteViews = RemoteViews(
            context.packageName,
            R.layout.add_credential_widget_small
        ).apply {
            setOnClickPendingIntent(R.id.add_credential_widget_layout_id, pendingIntent)
        }

        val normalView: RemoteViews = RemoteViews(
            context.packageName,
            R.layout.add_credential_widget
        ).apply {
            setOnClickPendingIntent(R.id.add_credential_widget_layout_id, pendingIntent)
        }

        val wideView: RemoteViews = RemoteViews(
            context.packageName,
            R.layout.add_credential_widget_wide
        ).apply {
            setOnClickPendingIntent(R.id.add_credential_widget_layout_id, pendingIntent)
        }

        val viewMapping =  mapOf(
            SizeF(150f, 80f) to smallView,
            SizeF(250f, 80f) to normalView,
            SizeF(300f, 80f) to wideView
        )

        val views = RemoteViews(viewMapping)
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}

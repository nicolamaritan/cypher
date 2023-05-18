package com.project.passwordmanager.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import com.project.passwordmanager.R

/**
 * Implementation of App Widget functionality.
 */
class PasswordManagerWidget : AppWidgetProvider() {

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


    internal fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val pwmServiceIntent = Intent(context, PasswordManagerWidgetService::class.java).apply {
            //Add the widget ID to the intent extras
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)

            //with this line, the system can distinguish between the instances of the widgets
            data = Uri.parse(toUri(Intent.URI_INTENT_SCHEME))
        }


        val views = RemoteViews(context.packageName, R.layout.password_manager_widget).apply {
            /*
                Set up the RemoteViews object to use a RemoteViews adapter.
                This adapter connects to a RemoteViewsService through the specified intent.
                This is how you populate the data
            */
            setRemoteAdapter(R.id.widget_listview, pwmServiceIntent)

            /*
                The empty view is displayed when the collection has no items.
                It must be in the same layout used to instantiate the
                RemoteViews object.
            */
            setEmptyView(R.id.widget_listview, R.id.empty_listview)
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}
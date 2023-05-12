package com.project.passwordmanager.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import com.project.passwordmanager.PasswordManagerWidgetService
import com.project.passwordmanager.R

/**
 * Implementation of App Widget functionality.
 */
class PasswordManagerWidget : AppWidgetProvider() {

    //Implement the onUpdate() callback method to set the RemoteViewsService as the remote adapter for the widget collection
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {

        //Update each of the widgets with the remote adapter
        //(appWidgetId corresponds to all the widget instances we have on the screen
        appWidgetIds.forEach { appWidgetId ->

            //Set up the intent that starts the PasswordManagerWidgetService, which
            //provides the views for this collection
            val intent = Intent(context, PasswordManagerWidgetService::class.java).apply {
                //Add the widget ID to the intent extras
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)

                //with this line, the system can distinguish between the instances of the widgets
                data = Uri.parse(toUri(Intent.URI_INTENT_SCHEME))
            }
            //Instantiate the RemoteViews object for the widget layout
            val views = RemoteViews(context.packageName, R.layout.password_manager_widget).apply {
                //Set up the RemoteViews object to use a RemoteViews adapter.
                //This adapter connects to a RemoteViewsService through the
                //specified intent.
                //This is how you populate the data
                setRemoteAdapter(R.id.widget_listview, intent)

                //The empty view is displayed when the collection has no items.
                //It must be in the same layout used to instantiate the
                //RemoteViews object
                setEmptyView(R.id.widget_listview, R.id.empty_listview)
            }
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
        val widgetText = context.getString(R.string.appwidget_text)
        // Construct the RemoteViews object
        val views = RemoteViews(context.packageName, R.layout.password_manager_widget)
        views.setTextViewText(R.id.empty_listview, widgetText)

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}
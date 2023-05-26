package com.project.passwordmanager.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.SizeF
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import com.project.passwordmanager.R

/**
 * Implementation of App Widget functionality for the Password Manager widget.
 * This class extends AppWidgetProvider and provides methods to handle widget updates and events.
 */
class PasswordManagerWidget : AppWidgetProvider() {

    /**
     * Called when the widget is being updated.
     *
     * @param context The context in which the receiver is running.
     * @param appWidgetManager The AppWidgetManager instance for managing the widget.
     * @param appWidgetIds An array of widget IDs for the widgets that need to be updated.
     */
    @RequiresApi(Build.VERSION_CODES.S)
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

    /**
     * Called when the first instance of the widget is created.
     *
     * @param context The context in which the receiver is running.
     */
    override fun onEnabled(context: Context)
    {
        // Enter relevant functionality for when the first widget is created
    }

    /**
     * Called when the last instance of the widget is disabled.
     *
     * @param context The context in which the receiver is running.
     */
    override fun onDisabled(context: Context)
    {
        // Enter relevant functionality for when the last widget is disabled
    }

    /**
     * Sets up the RemoteViews adapter for the widget.
     *
     * @param remoteViews The RemoteViews object for the widget.
     * @param serviceIntent The intent for the RemoteViewsService.
     */
    private fun setupRemoteAdapter(remoteViews: RemoteViews, serviceIntent: Intent) {
        /*
            Set up the RemoteViews object to use a RemoteViews adapter.
            This adapter connects to a RemoteViewsService through the specified intent.
            This is how you populate the data.
        */
        remoteViews.setRemoteAdapter(R.id.widget_listview, serviceIntent)

        /*
            The empty view is displayed when the collection has no items.
            It must be in the same layout used to instantiate the RemoteViews object.
        */
        remoteViews.setEmptyView(R.id.widget_listview, R.id.empty_listview)
    }

    /**
     * Updates the widget with the specified ID.
     *
     * @param context The context in which the receiver is running.
     * @param appWidgetManager The AppWidgetManager instance for managing the widget.
     * @param appWidgetId The ID of the widget to be updated.
     */
    @RequiresApi(Build.VERSION_CODES.S)
    internal fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val serviceIntent = Intent(context, PasswordManagerWidgetService::class.java).apply {
            //Add the widget ID to the intent extras
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)

            //with this line, the system can distinguish between the instances of the widgets
            data = Uri.parse(toUri(Intent.URI_INTENT_SCHEME))
        }

        /**
         *  RemoteViews specifying separate layouts for orientation or size cannot be modified.
         *  Instead, fully configure each layouts individually before constructing the combined.
         *  Therefore, each layout should be configured BEFORE invoking RemoteViews(viewMapping).
         * */
        val smallView = RemoteViews(context.packageName, R.layout.password_manager_widget).apply {
            setupRemoteAdapter(this, serviceIntent)
        }

        val tallView = RemoteViews(context.packageName, R.layout.password_manager_widget).apply {
            setupRemoteAdapter(this, serviceIntent)
        }

        val wideView = RemoteViews(context.packageName, R.layout.password_manager_widget).apply {
            setupRemoteAdapter(this, serviceIntent)
        }

        // Maps the sizes to each view.
        val viewMapping: Map<SizeF, RemoteViews> = mapOf(
            SizeF(80f, 80f) to smallView,
            SizeF(80f, 350f) to tallView,
            SizeF(240f, 140f) to wideView
        )

        // The returned RemoteViews is chosen based on the widget size, according to the mapping
        val views = RemoteViews(viewMapping)

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}
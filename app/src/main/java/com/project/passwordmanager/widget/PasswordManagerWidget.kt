package com.project.passwordmanager.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.util.SizeF
import android.widget.RemoteViews
import com.project.passwordmanager.activities.MainActivity
import com.project.passwordmanager.R
import com.project.passwordmanager.activities.UnlockWidgetActivity
import com.project.passwordmanager.common.WidgetPreferencesManager

/**
 * Implementation of App Widget functionality for the Password Manager widget.
 * This class extends AppWidgetProvider and provides methods to handle widget updates and events.
 */
class PasswordManagerWidget : AppWidgetProvider()
{
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

    override fun onEnabled(context: Context)
    {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context)
    {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context?, intent: Intent?)
    {
        when(intent!!.action)
        {
            AppWidgetManager.ACTION_APPWIDGET_DELETED ->
            {
                // When the widget is deleted, remove all its preferences
                val appWidgetId: Int = intent.extras!!.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID)
                Log.d(TAG, "Deleted widget ID $appWidgetId")
                WidgetPreferencesManager(context!!, appWidgetId).clear()
            }
        }

        super.onReceive(context, intent)
    }


    /**
     * Updates the widget with the specified ID.
     *
     * @param context The context in which the receiver is running.
     * @param appWidgetManager The AppWidgetManager instance for managing the widget.
     * @param appWidgetId The ID of the widget to be updated.
     */
    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {

        val viewMapping = getWidgetViewMapping(context, appWidgetId)
        // The returned RemoteViews is chosen based on the widget size, according to the mapping
        val views = RemoteViews(viewMapping)

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    companion object
    {
        val TAG = PasswordManagerWidget::class.java.toString()
        const val ITEM_POSITION = "item_position"
        const val ITEM_CLICK_ACTION = "item_click"
        const val SEE_ALL_ACTION = "see_all"
        const val ITEM_PASSWORD = "item_password"
        const val ITEM_SERVICE = "item_service"
        const val ITEM_USERNAME = "item_username"

        /**
         * Initializes the RemoteViews adapter and sets up the widget's remote adapter and empty view.
         *
         * @param remoteViews The RemoteViews object representing the widget's layout.
         * @param context The context in which the receiver is running.
         * @param appWidgetId The ID of the widget to be updated.
         */
        private fun initRemoteAdapter(remoteViews: RemoteViews, context: Context, appWidgetId: Int)
        {
            val serviceIntent = Intent(context, PasswordManagerWidgetService::class.java).apply {
                //Add the widget ID to the intent extras
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)

                //with this line, the system can distinguish between the instances of the widgets
                data = Uri.parse(toUri(Intent.URI_INTENT_SCHEME))
            }

            remoteViews.setRemoteAdapter(R.id.widget_listview, serviceIntent)
            remoteViews.setEmptyView(R.id.widget_listview, R.id.empty_listview)
        }


        /**
         * Sets up the item click PendingIntent for the widget.
         *
         * @param remoteViews The RemoteViews object representing the widget's layout.
         * @param context The context in which the receiver is running.
         * @param appWidgetId The ID of the widget to be updated.
         */
        private fun setupItemClick(remoteViews: RemoteViews, context: Context, appWidgetId: Int)
        {
            val itemClickIntent = Intent(context, UnlockWidgetActivity::class.java).apply {
                action = ITEM_CLICK_ACTION
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            }

            // Special intent performing a broadcast
            val pendingIntent = PendingIntent.getActivity(
                context,
                appWidgetId,
                itemClickIntent,
                PendingIntent.FLAG_MUTABLE
            )

            // The PendingIntentTemplate is combined with the FillInIntent in AppWidgetService
            remoteViews.setPendingIntentTemplate(R.id.widget_listview, pendingIntent)
        }

        private fun setupWidgetName(remoteViews: RemoteViews, context: Context, appWidgetId: Int)
        {
            var widgetName = WidgetPreferencesManager(context, appWidgetId).getName()
            if (widgetName.isBlank())
            {
                widgetName = "My passwords"
            }

            remoteViews.setTextViewText(R.id.widget_name, widgetName)
        }

        private fun setupCredentialsNumber(remoteViews: RemoteViews, context: Context, appWidgetId: Int)
        {
            val savedAddedIds = WidgetPreferencesManager(context, appWidgetId).getAddedIds()
            remoteViews.setTextViewText(R.id.credentials_number, "Stored credentials: " + savedAddedIds.size)
        }

        private fun setupSeeAllButton(remoteViews: RemoteViews, context: Context, appWidgetId: Int)
        {
            val seeAllClickIntent = Intent(context, MainActivity::class.java).apply {
                action = SEE_ALL_ACTION
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            }

            // Special intent performing a broadcast
            val pendingIntent = PendingIntent.getActivity(
                context,
                appWidgetId,
                seeAllClickIntent,
                PendingIntent.FLAG_MUTABLE
            )

            remoteViews.setOnClickPendingIntent(R.id.see_all_button, pendingIntent)
        }

        fun getWidgetViewMapping(context: Context, appWidgetId: Int): Map<SizeF, RemoteViews> {
            /**
             *  RemoteViews specifying separate layouts for orientation or size cannot be modified.
             *  Instead, fully configure each layouts individually before constructing the combined.
             *  Therefore, each layout should be configured BEFORE invoking RemoteViews(viewMapping).
             * */
            val smallView =
                RemoteViews(context.packageName, R.layout.password_manager_widget).apply {
                    initRemoteAdapter(this, context, appWidgetId)
                    setupItemClick(this, context, appWidgetId)
                    setupWidgetName(this, context, appWidgetId)
                }

            val tallView =
                RemoteViews(context.packageName, R.layout.password_manager_widget_tall).apply {
                    initRemoteAdapter(this, context, appWidgetId)
                    setupItemClick(this, context, appWidgetId)
                    setupWidgetName(this, context, appWidgetId)
                    setupCredentialsNumber(this, context, appWidgetId)
                }

            val wideView =
                RemoteViews(context.packageName, R.layout.password_manager_widget_wide).apply {
                    initRemoteAdapter(this, context, appWidgetId)
                    setupItemClick(this, context, appWidgetId)
                    setupWidgetName(this, context, appWidgetId)
                    setupCredentialsNumber(this, context, appWidgetId)
                    setupSeeAllButton(this, context, appWidgetId)
                }

            // Maps the sizes to each view.
            return mapOf(
                SizeF(80f, 80f) to smallView,
                SizeF(80f, 350f) to tallView,
                SizeF(240f, 200f) to wideView
            )
        }



    }
}
package com.project.passwordmanager.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.SizeF
import android.widget.RemoteViews
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.project.passwordmanager.R
import com.project.passwordmanager.UnlockWidgetActivity
import com.project.passwordmanager.model.Credential
import com.project.passwordmanager.model.CredentialDao
import com.project.passwordmanager.model.CredentialDatabase
import com.project.passwordmanager.model.WidgetData

/**
 * Implementation of App Widget functionality for the Password Manager widget.
 * This class extends AppWidgetProvider and provides methods to handle widget updates and events.
 */
class PasswordManagerWidget : AppWidgetProvider() {

    private lateinit var allShoppingItems: LiveData<List<Credential>>
    private var shoppingItemList = ArrayList<Credential>()
    private lateinit var dao: CredentialDao

    /**
     * Called when the widget is being updated.
     *
     * @param context The context in which the receiver is running.
     * @param appWidgetManager The AppWidgetManager instance for managing the widget.
     * @param appWidgetIds An array of widget IDs for the widgets that need to be updated.
     */
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        dao = CredentialDatabase.getInstance(context).credentialDao
        allShoppingItems = dao.getAll()
        allShoppingItems.observeForever { list ->
            updateList(list)
        }

        // Notify that data has changed
        val widgetManager = AppWidgetManager.getInstance(context.applicationContext)
        widgetManager.notifyAppWidgetViewDataChanged(
            widgetManager.getAppWidgetIds(
                ComponentName(
                    context.applicationContext.packageName,
                    PasswordManagerWidget::class.java.name)),
            R.id.widget_listview
        )

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

    override fun onReceive(context: Context?, intent: Intent?)
    {
        when(intent!!.action)
        {
            ITEM_CLICK_ACTION ->
            {
                val appWidgetId: Int = intent.extras!!.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID)
                val position = intent.extras!!.getInt(ITEM_POSITION)
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

            LOCK_ACTION ->
            {
                val appWidgetId: Int = intent.extras!!.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID)
                val widgetData = WidgetData.getWidgetData(appWidgetId)

                Toast.makeText(
                    context,
                    "Widget safely locked.",
                    Toast.LENGTH_SHORT
                ).show()

                widgetData.lock()

                // Notify Service
                val appWidgetManager = AppWidgetManager.getInstance(context)
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_listview)
            }

            AppWidgetManager.ACTION_APPWIDGET_DELETED ->
            {
                val appWidgetId: Int = intent.extras!!.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID)
                Log.d(TAG, "Deleted widget ID $appWidgetId")
            }
        }


        super.onReceive(context, intent)
    }


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
        val itemClickIntent = Intent(context, PasswordManagerWidget::class.java).apply {
            action = ITEM_CLICK_ACTION
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        }

        // Special intent performing a broadcast
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            appWidgetId,
            itemClickIntent,
            PendingIntent.FLAG_MUTABLE
        )

        // The PendingIntentTemplate is combined with the FillInIntent in AppWidgetService
        remoteViews.setPendingIntentTemplate(R.id.widget_listview, pendingIntent)
    }

    private fun setupLockButtonClick(remoteViews: RemoteViews, context: Context, appWidgetId: Int)
    {
        if (!WidgetData.hasWidgetData(appWidgetId) || WidgetData.getWidgetData(appWidgetId).locked)
        {
            val unlockIntent = Intent(context, UnlockWidgetActivity::class.java)
            val unlockIntentExtras = Bundle()
            unlockIntentExtras.putInt(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            unlockIntent.putExtras(unlockIntentExtras)

            val unlockPendingIntent = PendingIntent.getActivity(
                context,
                appWidgetId,
                unlockIntent,
                PendingIntent.FLAG_MUTABLE
            )
            remoteViews.setOnClickPendingIntent(R.id.unlock_button, unlockPendingIntent)
            remoteViews.setTextViewText(
                R.id.unlock_button,
                context.getString(R.string.unlock)
            )
        }
        else
        {
            val lockIntent = Intent(context, PasswordManagerWidget::class.java).apply {
                action = LOCK_ACTION
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            }

            val lockPendingIntent = PendingIntent.getBroadcast(
                context,
                appWidgetId,
                lockIntent,
                PendingIntent.FLAG_MUTABLE
            )
            remoteViews.setOnClickPendingIntent(R.id.unlock_button, lockPendingIntent)
            remoteViews.setTextViewText(
                R.id.unlock_button,
                context.getString(R.string.lock))
        }

    }

    /**
     * Updates the widget with the specified ID.
     *
     * @param context The context in which the receiver is running.
     * @param appWidgetManager The AppWidgetManager instance for managing the widget.
     * @param appWidgetId The ID of the widget to be updated.
     */
    internal fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        /**
         *  RemoteViews specifying separate layouts for orientation or size cannot be modified.
         *  Instead, fully configure each layouts individually before constructing the combined.
         *  Therefore, each layout should be configured BEFORE invoking RemoteViews(viewMapping).
         * */
        val smallView = RemoteViews(context.packageName, R.layout.password_manager_widget).apply {
            initRemoteAdapter(this, context, appWidgetId)
            setupItemClick(this, context, appWidgetId)
            setupLockButtonClick(this, context, appWidgetId)
        }

        val tallView = RemoteViews(context.packageName, R.layout.password_manager_widget_tall).apply {
            initRemoteAdapter(this, context, appWidgetId)
            setupItemClick(this, context, appWidgetId)
            setupLockButtonClick(this, context, appWidgetId)
        }

        val wideView = RemoteViews(context.packageName, R.layout.password_manager_widget_wide).apply {
            initRemoteAdapter(this, context, appWidgetId)
            setupItemClick(this, context, appWidgetId)
            setupLockButtonClick(this, context, appWidgetId)
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

    companion object
    {
        val TAG = PasswordManagerWidget::class.java.toString()
        const val ITEM_POSITION = "item_position"
        const val ITEM_CLICK_ACTION = "item_click"
        const val LOCK_ACTION = "lock"

        fun updateAppWidget(context: Context, appWidgetId: Int)
        {
            // Update widget
            val updateWidgetIntent = Intent(context, PasswordManagerWidget::class.java)
            updateWidgetIntent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            val ids = intArrayOf(appWidgetId)   // Update ONLY one widget
            updateWidgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
            context.sendBroadcast(updateWidgetIntent)

            // Update the elements of the listview
            AppWidgetManager.getInstance(context)
                .notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_listview)
        }
    }

    private fun updateList(newList: List<Credential>) {
        shoppingItemList.clear()
        shoppingItemList.addAll(newList)
    }
}
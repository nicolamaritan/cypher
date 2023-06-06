package com.project.passwordmanager.widget.stats

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.project.passwordmanager.R
import com.project.passwordmanager.model.CredentialDatabase
import com.project.passwordmanager.model.CredentialRepository

/**
 * Implementation of App Widget functionality.
 */
class StatsWidget : AppWidgetProvider() {
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

    override fun onEnabled(context: Context) {}

    override fun onDisabled(context: Context) {}

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val credentialRepository = CredentialRepository(CredentialDatabase.getInstance(context).credentialDao)

        val views = RemoteViews(context.packageName, R.layout.stats_widget)

        // Observe the credentials from the repository
        credentialRepository.allCredentials.observeForever { credentials ->
            // Create a RemoteViews for each credential
            val builder = RemoteViews.RemoteCollectionItems.Builder()
            for (i in credentials.indices) {
                val credential = credentials[i]
                val itemViews =
                    RemoteViews(context.packageName, R.layout.stats_widget_stackview_item)
                itemViews.setTextViewText(R.id.service_tv, credential.service)
                itemViews.setTextViewText(R.id.username_tv, credential.username)
                itemViews.setTextViewText(R.id.time_passed_value, credential.date.toString())

                builder.addItem(i.toLong(), itemViews)
            }

            builder.setViewTypeCount(1)
            val remoteCollectionItems = builder.build()

            views.setRemoteAdapter(R.id.list_view, remoteCollectionItems)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

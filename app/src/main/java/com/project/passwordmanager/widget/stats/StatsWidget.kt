package com.project.passwordmanager.widget.stats

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.project.passwordmanager.R
import com.project.passwordmanager.common.DateChecker
import com.project.passwordmanager.common.DateStatus
import com.project.passwordmanager.model.CredentialDatabase
import com.project.passwordmanager.model.CredentialRepository
import java.time.LocalDate

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
        val credentialRepository = CredentialRepository(CredentialDatabase.getInstance(context).credentialDao)

        val views = RemoteViews(context.packageName, R.layout.stats_widget)

        // Observe the credentials from the repository
        credentialRepository.allCredentialSortedByDate.observeForever { credentials ->

            // Create a RemoteViews for each credential
            val builder = RemoteViews.RemoteCollectionItems.Builder()
            for (i in credentials.indices)
            {
                val credential = credentials[i]
                val itemViews =
                    RemoteViews(context.packageName, R.layout.stats_widget_listview_item)
                itemViews.setTextViewText(R.id.service_tv, credential.service)
                itemViews.setTextViewText(R.id.username_tv, credential.username)
                itemViews.setTextViewText(R.id.time_passed_value, credential.date.toString())

                // Gets color in function of the date status
                val timePassedColor = when (DateChecker(LocalDate.now()).checkDate(credential.date!!)){
                    DateStatus.NEW -> context.getColor(R.color.green)
                    DateStatus.THREE_MONTHS_OLD -> context.getColor(R.color.orange)
                    DateStatus.SIX_MONTHS_OLD -> context.getColor(R.color.red)
                }
                itemViews.setTextColor(R.id.time_passed_value, timePassedColor)

                builder.addItem(i.toLong(), itemViews)
            }

            // There is only one type of view. Different colors are handled programmatically
            builder.setViewTypeCount(1)

            // Setup remote adapter
            val remoteCollectionItems = builder.build()
            views.setRemoteAdapter(R.id.list_view, remoteCollectionItems)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

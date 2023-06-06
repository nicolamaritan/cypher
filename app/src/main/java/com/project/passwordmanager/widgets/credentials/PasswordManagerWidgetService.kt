package com.project.passwordmanager.widgets.credentials

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.project.passwordmanager.R
import com.project.passwordmanager.common.Logger
import com.project.passwordmanager.common.WidgetPreferencesManager
import com.project.passwordmanager.model.Credential
import com.project.passwordmanager.model.CredentialDao
import com.project.passwordmanager.model.CredentialDatabase
import com.project.passwordmanager.model.CredentialRepository

class PasswordManagerWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return PasswordManagerWidgetItemFactory(applicationContext, intent)
    }

    inner class PasswordManagerWidgetItemFactory(
        private val context: Context,
        intent: Intent?
    ) : RemoteViewsFactory {

        private var credentialDao: CredentialDao = CredentialDatabase.getInstance(context).credentialDao
        private val credentialRepository: CredentialRepository = CredentialRepository(credentialDao)
        private lateinit var allCredentials: LiveData<List<Credential>>
        private var credentialsItemList = ArrayList<Credential>()

        private val appWidgetId: Int = intent?.getIntExtra(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID

        override fun onCreate()
        {
            Logger.logCallback(TAG, "onCreate", appWidgetId)
            allCredentials = credentialRepository.allCredentials
            allCredentials.observeForever(credentialsObserver)
        }

        override fun onDataSetChanged()
        {
            Logger.logCallback(TAG, "onDataSetChanged", appWidgetId)

            credentialsItemList.clear()
            allCredentials.value?.let { list ->
                val savedAddedIds = WidgetPreferencesManager(context, appWidgetId).getAddedIds()
                val filteredList = list.filter { obj -> savedAddedIds.contains(obj.id) }
                credentialsItemList.addAll(filteredList)
            }

        }

        override fun onDestroy() {}

        override fun getCount(): Int
        {
            return credentialsItemList.size
        }

        override fun getViewAt(position: Int): RemoteViews
        {
            val view = RemoteViews(context.packageName, R.layout.widget_listview_item)
            val entry = credentialsItemList[position]

            view.setTextViewText(R.id.service, entry.service)
            view.setTextViewText(R.id.user, entry.username)

            val fillInIntentBundle = Bundle()
            fillInIntentBundle.putInt(PasswordManagerWidget.ITEM_POSITION, position)
            fillInIntentBundle.putString(PasswordManagerWidget.ITEM_SERVICE, entry.service)
            fillInIntentBundle.putString(PasswordManagerWidget.ITEM_USERNAME, entry.username)
            fillInIntentBundle.putString(PasswordManagerWidget.ITEM_PASSWORD, entry.password)

            val fillInIntent = Intent()
            fillInIntent.putExtras(fillInIntentBundle)
            view.setOnClickFillInIntent(R.id.widget_listview_item, fillInIntent)

            return view
        }

        override fun getLoadingView(): RemoteViews?
        {
            return null
        }

        override fun getViewTypeCount(): Int
        {
            return 1
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun hasStableIds(): Boolean
        {
            return true
        }

        private val credentialsObserver = Observer<List<Credential>> { credentials ->
            val savedAddedIds = WidgetPreferencesManager(context, appWidgetId).getAddedIds()
            val filteredList = credentials.filter { obj -> savedAddedIds.contains(obj.id) }

            credentialsItemList.clear()
            credentialsItemList.addAll(filteredList)
            AppWidgetManager.getInstance(context).notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_listview)
        }

    }
    companion object
    {
        private val TAG = PasswordManagerWidgetService::class.simpleName
    }
}

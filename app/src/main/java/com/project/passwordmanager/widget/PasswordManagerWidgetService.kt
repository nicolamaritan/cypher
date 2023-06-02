package com.project.passwordmanager.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.lifecycle.LiveData
import com.project.passwordmanager.R
import com.project.passwordmanager.common.Logger
import com.project.passwordmanager.common.WidgetPreferencesManager
import com.project.passwordmanager.model.Credential
import com.project.passwordmanager.model.CredentialDao
import com.project.passwordmanager.model.CredentialDatabase

//WATCH THE MANIFEST!

/**
 * A RemoteViewsService that provides a widget that displays the user and password data
 * for each service in the Password Manager app.
 * This widget uses a RemoteViewsFactory to provide the data to the widget, which is
 * responsible for creating the RemoteViews that will be displayed in the widget.
 * @see RemoteViewsService
 * @see RemoteViewsService.RemoteViewsFactory
*/
class PasswordManagerWidgetService: RemoteViewsService() {

    /**
     * Called when the widget is created, this method sets up the RemoteViewsFactory
     * to provide the data for the widget.
     * @param intent The intent that was used to start the service.
     * @return A new instance of PasswordManagerWidgetItemFactory to act as the RemoteViewsFactory for the widget.
     * @throws NullPointerException if the intent passed in is null.
     * @see RemoteViewsService.RemoteViewsFactory
     */
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory
    {
        //equivalent to the recyclerview adapter(widgets, though, belong to a different process compared to the app)
        return PasswordManagerWidgetItemFactory(applicationContext, intent!!)
    }

    /**
     * A RemoteViewsFactory that provides the data for the PasswordManagerWidgetService.
     * When an intent asks the PasswordManagerWidgetService a PasswordManagerWidgetItemFactory,
     * the intent is passed as parameter to the factory's constructor. The
     * intent contains info about the widget, such as the widget's id.
     * @param context The application context.
     * @param intent The intent which requested the factory
     * @see RemoteViewsService.RemoteViewsFactory
     */
    class PasswordManagerWidgetItemFactory(
        private val context: Context,
        intent: Intent
    ): RemoteViewsFactory
    {
        private var credentialDao: CredentialDao = CredentialDatabase.getInstance(context).credentialDao
        private lateinit var allCredentials: LiveData<List<Credential>>
        private var credentialsItemList = ArrayList<Credential>()


        private val appWidgetId: Int = intent.getIntExtra(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID)


        private fun updateList(newList: List<Credential>)
        {
            Log.d(TAG, "updateList invoked")

            val savedAddedIds = WidgetPreferencesManager(context, appWidgetId).getAddedIds()
            val filteredList = newList.filter { obj -> savedAddedIds.contains(obj.id) }

            if (filteredList.isEmpty())
                return

            credentialsItemList.clear()
            credentialsItemList.addAll(filteredList)
            onDataSetChanged()
        }

        override fun onCreate()
        {
            Logger.logCallback(TAG, "onCreate", appWidgetId)
            allCredentials = credentialDao.getAll()
            allCredentials.observeForever{ list ->
                updateList(list)
            }
        }

        override fun onDataSetChanged()
        {
            Logger.logCallback(TAG, "onDataSetChanged", appWidgetId)
            allCredentials = credentialDao.getAll()
            allCredentials.value?.let { updateList(it) }
        }


        override fun onDestroy() {}

        override fun getCount(): Int
        {
            return credentialsItemList.size
        }

        override fun getViewAt(position: Int): RemoteViews
        {
            //takes the view to display remotely in the widget
            val view = RemoteViews(context.packageName, R.layout.widget_listview_item)
            val entry = credentialsItemList[position]

            //set the text in the view, taking the id of the TextView and the element to insert in it
            view.setTextViewText(R.id.service, entry.service)
            view.setTextViewText(R.id.user, entry.username)
            view.setTextViewText(
                R.id.password,
                //if (!entry.visible) context.getString(R.string.locked_password) else entry.password
                context.getString(R.string.locked_password)
            )

            /*
            * Set FillInIntent for clicking on the unlock button.
            * */
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

        /**
         * Returns the loading view to display while the widget is loading.
         *
         * @return the loading view to display while the widget is loading
         */
        override fun getLoadingView(): RemoteViews?
        {
            return null
        }

        /**
         * Returns the number of different types of views the factory can provide.
         * In this case, there is only one type of view.
         *
         * @return the number of different types of views the factory can provide
         */
        override fun getViewTypeCount(): Int
        {
            return 1
        }

        /**
         * Returns the ID of the item at the given position in the widget.
         *
         * @param position the position of the item in the widget
         * @return the ID of the item at the given position in the widget
         */
        override fun getItemId(position: Int): Long
        {
            return position.toLong()
        }

        /**
         * Indicates whether the items in the widget have a stable ID.
         * In this case, it returns true.
         *
         * @return true if the item at the given position has a stable ID, false otherwise
         */
        override fun hasStableIds(): Boolean
        {
            return true
        }

        companion object
        {
            private val TAG = PasswordManagerWidgetItemFactory::class.simpleName
        }
    }
}
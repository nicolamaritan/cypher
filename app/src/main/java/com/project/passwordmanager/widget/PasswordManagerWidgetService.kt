package com.project.passwordmanager.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.project.passwordmanager.R
import com.project.passwordmanager.common.Logger
import com.project.passwordmanager.common.ToyDataWidgetDataInitializer
import com.project.passwordmanager.model.WidgetData

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
        /**
         * Obtains the widgetId from the intent which requested
         * the factory. The intent contains such information.
         */
        private val appWidgetId: Int = intent.getIntExtra(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID)

        /**
         * Holds reference to its WidgetData.
         */
        private var widgetData: WidgetData? = null

        /**
         * Called when the factory is first created.
         * This method should not perform any heavy lifting,
         * such as downloading or creating content.
         */
        override fun onCreate()
        {
            Logger.logCallback(TAG, "onCreate", appWidgetId)
            /*
                In onCreate(), set up any connections or cursors to your data
                source. Heavy lifting, such as downloading or creating content,
                must be deferred to onDataSetChanged() or getViewAt(). Taking
                more than 20 seconds on this call results in an ANR
            */

            WidgetData.createWidgetData(appWidgetId)
            ToyDataWidgetDataInitializer.initialize(appWidgetId)    // Populating WD with toy entries
            widgetData = WidgetData.getWidgetData(appWidgetId)
        }

        /**
         * Called when the data set changes. This method should be used to notify the factory
         * that the data has changed and needs to be updated.
         */
        override fun onDataSetChanged() {}

        /**
         * Called when the factory is destroyed. This method should be used to clean up any
         * resources used by the factory.
         */
        override fun onDestroy() {}

        /**
         * Gets the number of items in the list that will be displayed in the widget.
         *
         * @return The number of items in the list.
         */
        override fun getCount(): Int
        {
            return widgetData!!.size()
        }

        /**
         * Gets the view to display at the given position in the widget.
         *
         * @param position The position of the view to display.
         * @return The RemoteViews to display at the given position.
         */
        override fun getViewAt(position: Int): RemoteViews
        {
            //WARNING! As this is not a RecyclerView, the list in the widget will be less efficient! So, don't put a large amount of data inside!

            //takes the view to display remotely in the widget
            val view = RemoteViews(context.packageName, R.layout.widget_listview_item)
            val entry = widgetData!!.getEntry(position)

            //set the text in the view, taking the id of the TextView and the element to insert in it
            view.setTextViewText(R.id.service, entry.service)
            view.setTextViewText(R.id.user, entry.username)
            view.setTextViewText(R.id.password, entry.password)

            /*
            * Set FillInIntent for clicking on the unlock button.
            * */
            val fillInIntentBundle = Bundle()
            fillInIntentBundle.putInt("credential_position", position)
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
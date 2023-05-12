package com.project.passwordmanager

import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.widget.RemoteViews
import android.widget.RemoteViewsService

//WATCH THE MANIFEST!

class PasswordManagerWidgetService: RemoteViewsService() {

    //equivalent to the recyclerview adapter(widgets, though, belong to a different process compared to the app)
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return PasswordManagerWidgetItemFactory(applicationContext, intent!!)
    }

    class PasswordManagerWidgetItemFactory(
        private val context: Context,
        intent: Intent
    ): RemoteViewsFactory{
        private val appNames = Datasource(this.context).getAppNameList()
        private val userData = Datasource(this.context).getUserList()
        private val pwData = Datasource(this.context).getPasswordList()

        //In onCreate(), set up any connections or cursors to your data
        //source. Heavy lifting, such as downloading or creating content,
        //must be deferred to onDataSetChanged() or getViewAt(). Taking
        //more than 20 seconds on this call results in an ANR
        override fun onCreate() {
            //tocca mettere view binding etc me sa
        }

        override fun onDataSetChanged() {
            TODO("Not yet implemented")
        }

        override fun onDestroy() {
            TODO("Not yet implemented")
        }

        override fun getCount(): Int {
            return userData.size
        }

        //WARNING! As this is not a RecyclerView, the list in the widget will be less efficient! So, don't put a large amount of data inside!
        override fun getViewAt(position: Int): RemoteViews {
            //takes the view to display remotely in the widget
            val view = RemoteViews(context.packageName, R.layout.password_manager_item)

            //set the text in the view, taking the id of the TextView and the element to insert in it
            view.setTextViewText(R.id.app_name, appNames[position])
            view.setTextViewText(R.id.user, userData[position])
            view.setTextViewText(R.id.password, pwData[position])

            //SystemClock.sleep(500)        NON SO WHY
            return view
        }

        override fun getLoadingView(): RemoteViews {
            TODO("Not yet implemented")
        }

        override fun getViewTypeCount(): Int {
            return 1
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()     //NON SO WHY
        }

        override fun hasStableIds(): Boolean {
            return true
        }

    }
}
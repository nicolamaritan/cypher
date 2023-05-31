package com.project.passwordmanager.viewmodels

import android.widget.CheckBox
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.project.passwordmanager.R
import com.project.passwordmanager.adapters.WidgetConfigurationCredentialsAdapter
import com.project.passwordmanager.model.CredentialDao
import com.project.passwordmanager.model.Entry
import com.project.passwordmanager.model.WidgetData

/**
 * ViewModel class for the widget configuration screen.
 * Manages the data and operations related to the widget configuration.
 *
 * @param dao The DAO (Data Access Object) for retrieving credential data.
 */
class WidgetConfigurationViewModel(private val dao: CredentialDao) : ViewModel() {


    // List of credentials fetched from the DAO.
    val credentials = dao.getAll()

    /**
     * Retrieves the list of indices of the credentials to be added based on the user selection.
     *
     * @param recyclerView The RecyclerView displaying the credentials.
     * @param appWidgetId The ID of the app widget being configured.
     * @return The list of indices of the credentials to be added.
     */
    fun getToBeAddedIndex(recyclerView: RecyclerView, appWidgetId: Int): List<Int> {
        val toBeAddedIndex = mutableListOf<Int>()
        var checkedCount = 0
        for (i in 0 until recyclerView.childCount) {
            val viewHolder = recyclerView.findViewHolderForLayoutPosition(i)!!
            val checkBox = viewHolder.itemView.findViewById<CheckBox>(R.id.add_cb)
            if (checkBox.isChecked) {
                checkedCount++
                toBeAddedIndex.add(i)
            }
        }
        return toBeAddedIndex
    }

    /**
     * Initializes the widget data with the selected credentials.
     *
     * @param toBeAddedIndex The list of indices of the credentials to be added.
     * @param adapter The adapter for the RecyclerView displaying the credentials.
     * @param appWidgetId The ID of the app widget being configured.
     */
    fun initializeWidgetData(
        toBeAddedIndex: List<Int>,
        adapter: WidgetConfigurationCredentialsAdapter,
        appWidgetId: Int
    ) {
        WidgetData.createWidgetData(appWidgetId)

        for (i in toBeAddedIndex) {
            val credential = adapter.data[i]
            val entry = Entry(credential.service, credential.username, credential.password)
            WidgetData.getWidgetData(appWidgetId).addEntry(entry)
        }
    }
}
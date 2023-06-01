package com.project.passwordmanager.viewmodels

import android.widget.CheckBox
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.project.passwordmanager.R
import com.project.passwordmanager.model.CredentialDao

/**
 * ViewModel class for the widget configuration screen.
 * Manages the data and operations related to the widget configuration.
 *
 * @param dao The DAO (Data Access Object) for retrieving credential data.
 */
class WidgetConfigurationViewModel(private val dao: CredentialDao) : ViewModel() {


    // List of credentials fetched from the DAO.
    val credentials = dao.getAll()

    fun getToBeAddedIndexes(recyclerView: RecyclerView): List<Int> {
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

    fun getToBeAddedIds(toBeAddedIndexes: List<Int>) : List<Long>
    {
        val credentialList = credentials.value
        val toBeAddedIds = toBeAddedIndexes.map {
            credentialList!![it].id
        }
        return toBeAddedIds
    }
}
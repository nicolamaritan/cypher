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
}
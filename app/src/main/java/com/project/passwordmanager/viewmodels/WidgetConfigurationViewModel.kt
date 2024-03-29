package com.project.passwordmanager.viewmodels

import androidx.lifecycle.ViewModel
import com.project.passwordmanager.model.CredentialDao

/**
 * ViewModel class for the widget configuration screen.
 * Manages the data and operations related to the widget configuration.
 *
 * @param dao The `CredentialDao` for retrieving credential data.
 */
class WidgetConfigurationViewModel(private val dao: CredentialDao) : ViewModel()
{
    // List of credentials fetched from the DAO.
    val credentials = dao.getAll()

    /**
     * Contains the ids checked in the RecyclerView to avoid
     * state loss during screen rotation or other cases in which
     * the OS kills the activity.
     */
    var checkedCredentialsIds = mutableListOf<Int>()

    /**
     * Holds the inserted text for the widget name.
     */
    var widgetName = ""
}
package com.project.passwordmanager.viewmodels

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.project.passwordmanager.fragments.AddCredentialDialogFragment
import com.project.passwordmanager.model.CredentialDao

/**
 * This ViewModel class represents the credentials screen logic and data management.
 * It is responsible for providing data to the UI and handling user interactions.
 *
 * @param dao The CredentialDao used for data retrieval and manipulation.
 */
class CredentialsViewModel(dao: CredentialDao) : ViewModel() {
    /**.
     * Used for the RecyclerView to display all the tuples.
     */
    val credentials = dao.getAll()

    /**
     * Displays the dialog fragment for adding new credentials.
     *
     * @param fragmentManager The FragmentManager to show the dialog fragment.
     */
    fun showDialog(fragmentManager: FragmentManager) {
        val newFragment = AddCredentialDialogFragment()
        newFragment.show(fragmentManager, TAG)
    }

    /**
     * Companion object for static members and constants of the CredentialsViewModel class.
     */
    companion object {
        /**
         * The tag used for logging and identification of the CredentialsViewModel class.
         */
        private val TAG = CredentialsViewModel::javaClass.toString()
    }
}

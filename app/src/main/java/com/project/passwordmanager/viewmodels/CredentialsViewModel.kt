package com.project.passwordmanager.viewmodels

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.passwordmanager.fragments.AddCredentialDialogFragment
import com.project.passwordmanager.model.Credential
import com.project.passwordmanager.model.CredentialDao
import kotlinx.coroutines.launch

/**
 * This ViewModel class represents the credentials screen logic and data management.
 * It is responsible for providing data to the UI and handling user interactions.
 *
 * @param dao The CredentialDao used for data retrieval and manipulation.
 */
class CredentialsViewModel(val dao: CredentialDao) : ViewModel() {
    /**.
     * Used for the RecyclerView to display all the tuples.
     */
    val credentials = dao.getAll()

    /**
     * Displays the dialog fragment for adding new credentials.
     *
     * @param fragmentManager The FragmentManager to show the dialog fragment.
     */
    fun showDialog(fragmentManager: FragmentManager)
    {
        val newFragment = AddCredentialDialogFragment()
        newFragment.show(fragmentManager, TAG)
    }

    fun deleteCredential(credentialId: Int)
    {
        viewModelScope.launch {
            dao.delete(Credential(credentialId))
        }
    }

    companion object
    {
        private val TAG = CredentialsViewModel::javaClass.toString()
    }
}

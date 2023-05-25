package com.project.passwordmanager.viewmodels

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.project.passwordmanager.R
import com.project.passwordmanager.fragments.CredentialsDialogFragment
import com.project.passwordmanager.model.CredentialDao

class CredentialsViewModel(private val dao: CredentialDao) : ViewModel()
{
    private val credentials = dao.getAll()  //used for the RecyclerView to show all the tuples
    //take the credentials data and remap them in a more simple list of Strings
    val credentialsString = credentials.map() {
            credentials -> return@map(credentials)
    }

    fun showDialog(fragmentManager: FragmentManager)
    {
        val newFragment = CredentialsDialogFragment()
        newFragment.show(fragmentManager, TAG)
    }

    companion object
    {
        private val TAG = CredentialsViewModel::javaClass.toString()
    }
}
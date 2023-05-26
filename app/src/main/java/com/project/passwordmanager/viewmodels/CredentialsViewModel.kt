package com.project.passwordmanager.viewmodels

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.project.passwordmanager.fragments.CredentialsDialogFragment
import com.project.passwordmanager.model.CredentialDao

class CredentialsViewModel(dao: CredentialDao) : ViewModel()
{
    val credentials = dao.getAll()  //used for the RecyclerView to show all the tuples


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
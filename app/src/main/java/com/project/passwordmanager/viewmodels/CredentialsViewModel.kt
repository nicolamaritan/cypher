package com.project.passwordmanager.viewmodels

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.project.passwordmanager.R
import com.project.passwordmanager.fragments.CredentialsDialogFragment

class CredentialsViewModel : ViewModel()
{
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
package com.project.passwordmanager.viewmodels

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.project.passwordmanager.R
import com.project.passwordmanager.fragments.CredentialsDialogFragment


class CredentialsViewModel : ViewModel()
{
    fun showDialog(fragmentManager: FragmentManager)
    {
        val newFragment = CredentialsDialogFragment.newInstance(
            "R.string.alert_dialog_two_buttons_title"
        )
        newFragment.show(fragmentManager, "dialog")
    }
}
package com.project.passwordmanager.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.project.passwordmanager.R
import com.project.passwordmanager.common.Logger

class CredentialsDialogFragment(): DialogFragment()
{
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Logger.logCallback(TAG, "onCreateView", "CredentialsDialogFragment")
        return layoutInflater.inflate(R.layout.dialog_credentials, container, false)
    }

    companion object
    {
        private val TAG = CredentialsDialogFragment::javaClass.toString()
    }
}
package com.project.passwordmanager.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.project.passwordmanager.R
import com.project.passwordmanager.common.Logger

class CredentialsDialogFragment private constructor(): DialogFragment()
{
    companion object
    {
        fun newInstance(title: String): CredentialsDialogFragment
        {
            val frag = CredentialsDialogFragment()
            val args = Bundle()
            args.putString("title", title)
            frag.arguments = args
            return frag
        }

        private val TAG = CredentialsFragment::javaClass.toString()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.dialog_credentials, container, false)
    }
}
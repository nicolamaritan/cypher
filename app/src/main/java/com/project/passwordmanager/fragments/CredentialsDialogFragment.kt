package com.project.passwordmanager.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog
    {
        return AlertDialog.Builder(activity)
            .setIcon(androidx.constraintlayout.widget.R.drawable.abc_ic_arrow_drop_right_black_24dp)
            .setTitle("title")
            .setPositiveButton("R.string.alert_dialog_ok") {
                    dialogInterface, i -> Log.d(TAG, "OK")
            }
            .setNegativeButton("R.string.alert_dialog_cancel") {
                    dialogInterface, i -> Log.d(TAG, "CANCEL")
            }
            .create()
    }
}
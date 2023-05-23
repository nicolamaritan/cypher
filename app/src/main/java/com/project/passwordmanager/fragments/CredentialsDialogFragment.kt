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
import androidx.lifecycle.ViewModelProvider
import com.project.passwordmanager.R
import com.project.passwordmanager.common.Logger
import com.project.passwordmanager.databinding.DialogCredentialsBinding
import com.project.passwordmanager.factories.CredentialsDialogViewModelFactory
import com.project.passwordmanager.model.Credential
import com.project.passwordmanager.model.CredentialDao
import com.project.passwordmanager.model.CredentialDatabase
import com.project.passwordmanager.viewmodels.CredentialsDialogViewModel

class CredentialsDialogFragment(): DialogFragment()
{
    private var _binding: DialogCredentialsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Logger.logCallback(TAG, "onCreateView", "CredentialsDialogFragment")
        _binding = DialogCredentialsBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireActivity().application
        val dao = CredentialDatabase.getInstance(application).credentialDao
        val viewModelFactory = CredentialsDialogViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)[CredentialsDialogViewModel::class.java]

        binding.credentialsDialogViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.add.setOnClickListener{
            viewModel.addCredential()
            if (viewModel.closing)
            {
                dismiss()
            }
        }

        return view
    }

    override fun onDismiss(dialog: DialogInterface)
    {
        super.onDismiss(dialog)
    }


    companion object
    {
        private val TAG = CredentialsDialogFragment::javaClass.toString()
    }
}
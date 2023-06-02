package com.project.passwordmanager.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.project.passwordmanager.common.Logger
import com.project.passwordmanager.databinding.DialogModifyBinding
import com.project.passwordmanager.factories.ModifyDialogViewModelFactory
import com.project.passwordmanager.model.CredentialDatabase
import com.project.passwordmanager.viewmodels.ModifyDialogViewModel

class ModifyDialogFragment(private val credentialId : Long): DialogFragment() {
    private var _binding: DialogModifyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Logger.logCallback(TAG, "onCreateView", "CredentialsDialogFragment")

        // ViewBinding
        _binding = DialogModifyBinding.inflate(inflater, container, false)
        val view = binding.root

        // Instantiating the ViewModel
        val application = requireActivity().application
        val dao = CredentialDatabase.getInstance(application).credentialDao
        val viewModelFactory = ModifyDialogViewModelFactory(dao)
        val viewModel =
            ViewModelProvider(this, viewModelFactory)[ModifyDialogViewModel::class.java]

        // DataBinding
        binding.modifyDialogViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.modify.setOnClickListener {
            viewModel.modifyCredential(credentialId)
            if (viewModel.closing) {
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
        private val TAG = ModifyDialogFragment::javaClass.toString()
    }
}
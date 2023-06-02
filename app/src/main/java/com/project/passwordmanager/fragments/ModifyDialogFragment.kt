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

        /**
         * Sets a click listener on the delete button to delete a credential.
         */
        binding.delete.setOnClickListener {
            viewModel.deleteCredential(credentialId)

            // If the view model's closing flag is set to true, dismiss the view
            if (viewModel.closing) {
                dismiss()
            }
        }

        /*
        * Observes the toastStringId in the viewModel to show
        * a toast whenever the value changes.
        * Contains the id of the string to show.
        * */
        viewModel.toastStringId.observe(this) {
            it.let {
                val toastMessage = requireContext().getString(it)
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
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
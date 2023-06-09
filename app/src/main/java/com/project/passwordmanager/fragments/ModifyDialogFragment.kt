package com.project.passwordmanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.project.passwordmanager.common.Logger
import com.project.passwordmanager.common.Utils
import com.project.passwordmanager.databinding.DialogModifyBinding
import com.project.passwordmanager.factories.ModifyDialogViewModelFactory
import com.project.passwordmanager.model.CredentialDatabase
import com.project.passwordmanager.viewmodels.ModifyDialogViewModel

/**
 * Dialog which shows up when the user presses on the modify button
 * in the Credentials fragment.
 *
 * @param credentialId the id of the credential to modify
 */
class ModifyDialogFragment(private val credentialId : Int): DialogFragment()
{
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
            viewModel.modifyCredential(credentialId, Utils.getHashedMasterPassword(requireContext()))
            if (viewModel.closing)
            {
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
                Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show()
            }
        }

        return view
    }

    companion object
    {
        private val TAG = ModifyDialogFragment::class.java.simpleName
    }
}
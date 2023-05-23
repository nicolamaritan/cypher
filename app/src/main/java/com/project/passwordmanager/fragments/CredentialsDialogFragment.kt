package com.project.passwordmanager.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.project.passwordmanager.common.Logger
import com.project.passwordmanager.databinding.DialogCredentialsBinding
import com.project.passwordmanager.factories.CredentialsDialogViewModelFactory
import com.project.passwordmanager.model.CredentialDatabase
import com.project.passwordmanager.viewmodels.CredentialsDialogViewModel

/**
 * Dialog fragment for adding credentials in the password manager application.
 *
 * This dialog allows the user to enter new credentials, which are then added to the database.
 * It utilizes a ViewModel to handle user interactions, data validation, and database operations.
 *
 * @see CredentialsDialogViewModel
 */
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

        // ViewBinding
        _binding = DialogCredentialsBinding.inflate(inflater, container, false)
        val view = binding.root

        // Instantiating the ViewModel
        val application = requireActivity().application
        val dao = CredentialDatabase.getInstance(application).credentialDao
        val viewModelFactory = CredentialsDialogViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)[CredentialsDialogViewModel::class.java]

        // DataBinding
        binding.credentialsDialogViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.add.setOnClickListener{
            viewModel.addCredential()
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
        private val TAG = CredentialsDialogFragment::javaClass.toString()
    }
}
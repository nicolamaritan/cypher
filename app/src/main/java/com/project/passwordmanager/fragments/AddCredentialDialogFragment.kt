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
import com.project.passwordmanager.databinding.DialogCredentialsBinding
import com.project.passwordmanager.factories.AddCredentialDialogViewModelFactory
import com.project.passwordmanager.model.CredentialDatabase
import com.project.passwordmanager.viewmodels.AddCredentialDialogViewModel

/**
 * Dialog fragment for adding credentials in the password manager application.
 *
 * This dialog allows the user to enter new credentials, which are then added to the database.
 * It utilizes a ViewModel to handle user interactions, data validation, and database operations.
 *
 * @see AddCredentialDialogViewModel
 */
class AddCredentialDialogFragment : DialogFragment()
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
        val viewModelFactory = AddCredentialDialogViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)[AddCredentialDialogViewModel::class.java]

        // DataBinding
        binding.credentialsDialogViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.add.setOnClickListener{
            // Passes the true hashed password for the check
            if (viewModel.validateCredential() && viewModel.checkInsertedMasterPassword(Utils.getHashedMasterPassword(requireContext())))
            {
                if (viewModel.addCredential())
                {
                    dismiss()
                }
            }
            else
            {
                binding.insertedMasterPasswordTe.text.clear()
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
        private val TAG = AddCredentialDialogFragment::javaClass.toString()
    }
}
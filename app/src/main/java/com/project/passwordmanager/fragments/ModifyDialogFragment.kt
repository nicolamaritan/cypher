package com.project.passwordmanager.fragments

import android.os.Bundle
import android.util.Log
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
import com.project.passwordmanager.model.Credential
import com.project.passwordmanager.model.CredentialDatabase
import com.project.passwordmanager.viewmodels.ModifyDialogViewModel

/**
 * Dialog which shows up when the user presses on the modify button
 * in the Credentials fragment.
 *
 */
class ModifyDialogFragment: DialogFragment()
{
    private var _binding: DialogModifyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Logger.logCallback(TAG, "onCreateView", "CredentialsDialogFragment")
        Log.d(TAG, "$isStateSaved")
        val credential = Credential()
        credential.id = arguments!!.getInt(ID_KEY)
        credential.username = arguments!!.getString(USERNAME_KEY)!!
        credential.service = arguments!!.getString(SERVICE_KEY)!!
        credential.password = arguments!!.getString(PASSWORD_KEY)!!

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

        /* Updates the values in the viewModel only if it is the first instantiation,
         * therefore only if the viewModel has no previous values associated to it.
         */
        if (viewModel.firstInstantiation)
        {
            viewModel.firstInstantiation = false
            viewModel.newCredentialService = credential.service
            viewModel.newCredentialUsername = credential.username
        }

        binding.modify.setOnClickListener {
            viewModel.modifyCredential(credential.id, Utils.getHashedMasterPassword(requireContext()))
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
        private const val ID_KEY = "id"
        private const val USERNAME_KEY = "username"
        private const val SERVICE_KEY = "service"
        private const val PASSWORD_KEY = "password"

        fun newInstance(credential: Credential): ModifyDialogFragment
        {
            val args = Bundle()
            args.putInt(ID_KEY, credential.id)
            args.putString(USERNAME_KEY, credential.username)
            args.putString(SERVICE_KEY, credential.service)
            args.putString(PASSWORD_KEY, credential.password)
            val modifyDialogFragment = ModifyDialogFragment()
            modifyDialogFragment.arguments = args
            return modifyDialogFragment
        }
    }
}
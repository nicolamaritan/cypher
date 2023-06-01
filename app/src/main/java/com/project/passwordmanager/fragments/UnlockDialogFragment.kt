package com.project.passwordmanager.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.project.passwordmanager.common.Logger
import com.project.passwordmanager.databinding.DialogUnlockBinding
import com.project.passwordmanager.factories.UnlockDialogViewModelFactory
import com.project.passwordmanager.viewmodels.UnlockDialogViewModel

class UnlockDialogFragment: DialogFragment() {
    private var _binding: DialogUnlockBinding? = null
    private val binding get() = _binding!!
    var unlocked = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Logger.logCallback(TAG, "onCreateView", "UnlockDialogFragment")

        // ViewBinding
        _binding = DialogUnlockBinding.inflate(inflater, container, false)
        val view = binding.root

        // Instantiating the ViewModel
        val application = requireActivity().application
        val viewModelFactory = UnlockDialogViewModelFactory()
        val viewModel =
            ViewModelProvider(this, viewModelFactory)[UnlockDialogViewModel::class.java]

        // DataBinding
        binding.unlockDialogViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.unlockButton.setOnClickListener {

            /*
                Checks if the entered password matches the stored password in the view model.
                If the passwords match, a success message is displayed and the activity is finished,
                indicating that the widget has been unlocked. Otherwise, an error message is displayed,
                indicating that the entered password is incorrect.
             */
            if (viewModel.unlock(binding.insertedPasswordTe.text.toString()))
            {
                Toast.makeText(
                    application,
                    "Unlocked successfully.",
                    Toast.LENGTH_LONG
                ).show()
                unlocked = true
            }
            else
            {
                Toast.makeText(
                    application,
                    "Wrong password.",
                    Toast.LENGTH_LONG
                ).show()
            }
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
        private val TAG = CredentialsDialogFragment::javaClass.toString()
    }
}
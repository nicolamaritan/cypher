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
import com.project.passwordmanager.databinding.DialogUnlockBinding
import com.project.passwordmanager.factories.UnlockDialogViewModelFactory
import com.project.passwordmanager.viewmodels.UnlockDialogListener
import com.project.passwordmanager.viewmodels.UnlockDialogViewModel

class UnlockDialogFragment : DialogFragment(), UnlockDialogListener {
    private var _binding: DialogUnlockBinding? = null
    private val binding get() = _binding!!

    var unlocked = false

    // The listener for unlock events
    private var unlockDialogListener: UnlockDialogListener? = null

    /**
     * Sets the UnlockDialogListener to listen for unlock events.
     *
     * @param listener The UnlockDialogListener instance to set.
     */
    fun setUnlockDialogListener(listener: UnlockDialogListener) {
        unlockDialogListener = listener
    }

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
        val viewModel = ViewModelProvider(this, viewModelFactory)[UnlockDialogViewModel::class.java]
        viewModel.setUnlockDialogListener(this)

        // DataBinding
        binding.unlockDialogViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.unlockButton.setOnClickListener {
            viewModel.unlock(binding.insertedPasswordTe.text.toString())
        }

        return view
    }

    /**
     * Called when the password is successfully unlocked.
     * Shows a success message, sets the 'unlocked' flag to true,
     * dismisses the dialog, and notifies the listener of unlock success if available.
     */
    override fun onUnlockSuccess() {
        Toast.makeText(
            requireContext(),
            "Unlocked successfully.",
            Toast.LENGTH_LONG
        ).show()
        unlocked = true
        dismiss()

        // Notify the listener of unlock success
        if (unlockDialogListener != null) {
            unlockDialogListener!!.onUnlockSuccess()
        }
    }

    /**
     * Called when the entered password is incorrect.
     * Shows an error message indicating the wrong password.
     */
    override fun onUnlockFailure() {
        Toast.makeText(
            requireContext(),
            "Wrong password.",
            Toast.LENGTH_LONG
        ).show()
    }

    /**
     * Called when the dialog is dismissed.
     * Performs any necessary cleanup or actions after the dialog is dismissed.
     */
    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }

    /**
     * Companion object for the UnlockDialogFragment class.
     * Contains constant values and utilities related to the fragment.
     */
    companion object {
        /**
         * TAG used for logging.
         */
        private val TAG = UnlockDialogFragment::class.java.simpleName
    }
}

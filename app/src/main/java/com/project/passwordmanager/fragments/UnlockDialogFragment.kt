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
import com.project.passwordmanager.common.Utils
import com.project.passwordmanager.databinding.DialogUnlockBinding
import com.project.passwordmanager.factories.UnlockDialogViewModelFactory
import com.project.passwordmanager.listeners.UnlockDialogListener
import com.project.passwordmanager.viewmodels.UnlockDialogViewModel

/**
 * DialogFragment of the Dialog used to authenticate the user and
 * unlock a feature of the app. It provides an interface to access
 * the inserted master password
 * by the use.
 */
class UnlockDialogFragment : DialogFragment()
{
    private var _binding: DialogUnlockBinding? = null
    private val binding get() = _binding!!

    /* Saved in clear to be accessed by the outside to lock and unlock
    *
    * */
    var insertedMasterPassword: String = ""
        private set

    // The listener for unlock events
    private var unlockDialogListener: UnlockDialogListener? = null

    /**
     * Sets the UnlockDialogListener to listen for unlock events.
     *
     * @param listener The UnlockDialogListener instance to set.
     */
    fun setUnlockDialogListener(listener: UnlockDialogListener)
    {
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
        val viewModelFactory = UnlockDialogViewModelFactory()
        val viewModel = ViewModelProvider(this, viewModelFactory)[UnlockDialogViewModel::class.java]

        // DataBinding
        binding.unlockDialogViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.unlockButton.setOnClickListener {
            if (viewModel.unlock(binding.insertedPasswordTe.text.toString(),
                    Utils.getHashedMasterPassword(requireContext())))
            {
                insertedMasterPassword = binding.insertedPasswordTe.text.toString()
                unlockDialogListener!!.onUnlockSuccess()
                dismiss()
            }
            else
            {
                binding.insertedPasswordTe.text.clear()
            }
        }

        viewModel.toastStringId.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), getString(it), Toast.LENGTH_LONG).show()
        }

        return view
    }

    override fun onDismiss(dialog: DialogInterface)
    {
        // Clears the master password for security reasons
        insertedMasterPassword = ""
        super.onDismiss(dialog)
    }

    /**
     * Companion object for the UnlockDialogFragment class.
     * Contains constant values and utilities related to the fragment.
     */
    companion object
    {
        private val TAG = UnlockDialogFragment::class.java.simpleName
        const val UNLOCK_DIALOG_FRAGMENT_TAG = "Unlock_Dialog"
    }
}

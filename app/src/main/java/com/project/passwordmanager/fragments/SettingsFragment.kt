package com.project.passwordmanager.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProvider
import com.project.passwordmanager.R
import com.project.passwordmanager.common.Constants
import com.project.passwordmanager.common.CredentialsOrder
import com.project.passwordmanager.common.Utils
import com.project.passwordmanager.databinding.FragmentSettingsBinding
import com.project.passwordmanager.factories.SettingsViewModelFactory
import com.project.passwordmanager.model.CredentialDatabase
import com.project.passwordmanager.security.Hashing
import com.project.passwordmanager.viewmodels.SettingsViewModel

class SettingsFragment : Fragment()
{
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root

        //Get the view model
        val dao = CredentialDatabase.getInstance(requireContext()).credentialDao
        val viewModelFactory = SettingsViewModelFactory(dao)
        viewModel = ViewModelProvider(this, viewModelFactory)[SettingsViewModel::class.java]

        // Retrieves the saved option and set it as checked. It is not done by default
        val sharedPref = requireContext().getSharedPreferences(Constants.SYSTEM_PREFERENCES, Context.MODE_PRIVATE)
        val credentialsOrder = sharedPref.getInt(Constants.CREDENTIALS_ORDER, CredentialsOrder.CHRONOLOGICAL)
        binding.radioGroup.check(
            when(credentialsOrder)
            {
                CredentialsOrder.CHRONOLOGICAL -> R.id.chronological_order_rb
                CredentialsOrder.ALPHABETIC_USERNAME -> R.id.alphabetic_user_order_rb
                CredentialsOrder.ALPHABETIC_SERVICE -> R.id.alphabetic_service_order_rb
                else ->  R.id.chronological_order_rb
            }
        )

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedValue = when (checkedId)
            {
                R.id.chronological_order_rb -> CredentialsOrder.CHRONOLOGICAL
                R.id.alphabetic_user_order_rb -> CredentialsOrder.ALPHABETIC_USERNAME
                R.id.alphabetic_service_order_rb -> CredentialsOrder.ALPHABETIC_SERVICE
                else -> CredentialsOrder.CHRONOLOGICAL
            }

            // Save chosen persistent state in shared preferences
            requireContext().getSharedPreferences(Constants.SYSTEM_PREFERENCES, Context.MODE_PRIVATE)
                .edit().putInt(Constants.CREDENTIALS_ORDER, selectedValue).apply()
        }

        // Set the state of the dark mode switch
        val switchDarkMode: SwitchCompat = view.findViewById(R.id.dark_mode_switch)
        switchDarkMode.isChecked = isDarkModeEnabled()
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
            {
                enableDarkMode()
            }
            else
            {
                disableDarkMode()
            }
        }


        binding.confirmButton.setOnClickListener {
            val insertedOldMasterPassword = binding.oldMasterpasswordEt.text.toString()
            val insertedNewMasterPassword = binding.newMasterpasswordEt.text.toString()
            val confirmedNewMasterPassword = binding.confirmedNewMasterpasswordEt.text.toString()

            if(insertedOldMasterPassword.isBlank() || insertedNewMasterPassword.isBlank() || confirmedNewMasterPassword.isBlank()){
                Toast.makeText(
                    requireContext(),
                    getString(R.string.blank_password) + ". \n" +
                            getString(R.string.passwords_cannot_be_blank),
                    Toast.LENGTH_LONG
                ).show()
            }
            else
            {
                if (Hashing.sha256(insertedOldMasterPassword) != Utils.getHashedMasterPassword(requireContext()))
                {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.wrong_master_password),
                        Toast.LENGTH_LONG
                    ).show()
                }
                else
                {
                    if (insertedNewMasterPassword == confirmedNewMasterPassword)
                    {
                        // Actually accepts the master password
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.master_password_correctly_inserted),
                            Toast.LENGTH_LONG
                        ).show()
                        Utils.setHashedMasterPassword(requireContext(), insertedNewMasterPassword)
                        viewModel.updatePasswords(insertedOldMasterPassword, insertedNewMasterPassword)

                        // Clears inserted values
                        binding.oldMasterpasswordEt.setText("")
                        binding.newMasterpasswordEt.setText("")
                        binding.confirmedNewMasterpasswordEt.setText("")
                    }
                    else
                    {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.password_mismatch) + ". \n" +
                                    getString(R.string.entered_passwords_do_not_match),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

        return view
    }

    /**
     * Checks if dark mode is currently enabled.
     * @return Boolean indicating whether dark mode is enabled.
     */
    private fun isDarkModeEnabled(): Boolean {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }

    /**
     * Enables dark mode.
     */
    private fun enableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        requireActivity().recreate()
    }


    /**
     * Disables dark mode.
     */
    private fun disableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        requireActivity().recreate()
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}
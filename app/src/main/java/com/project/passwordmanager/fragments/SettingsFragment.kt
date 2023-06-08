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
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.project.passwordmanager.R
import com.project.passwordmanager.common.Constants
import com.project.passwordmanager.common.CredentialsOrder
import com.project.passwordmanager.common.Utils
import com.project.passwordmanager.databinding.FragmentSettingsBinding
import com.project.passwordmanager.factories.SettingsViewModelFactory
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
        viewModel = ViewModelProvider(this)[SettingsViewModel::class.java]

        //Get the view model
        val viewModelFactory = SettingsViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[SettingsViewModel::class.java]

        // Retrieves the saved option and set it as checked. It is not done by default
        val sharedPref = requireContext().getSharedPreferences(Constants.SYSTEM_PREFERENCES, Context.MODE_PRIVATE)
        val credentialsOrder = sharedPref.getInt(Constants.CREDENTIALS_ORDER, CredentialsOrder.CHRONOLOGICAL)
        binding.radioGroup.check(
            when(credentialsOrder)
            {
                CredentialsOrder.CHRONOLOGICAL -> R.id.radioButton
                CredentialsOrder.ALPHABETIC_USERNAME -> R.id.radioButton2
                CredentialsOrder.ALPHABETIC_SERVICE -> R.id.radioButton3
                else ->  R.id.radioButton
            }
        )

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedValue = when (checkedId)
            {
                R.id.radioButton -> CredentialsOrder.CHRONOLOGICAL
                R.id.radioButton2 -> CredentialsOrder.ALPHABETIC_USERNAME
                R.id.radioButton3 -> CredentialsOrder.ALPHABETIC_SERVICE
                else -> CredentialsOrder.CHRONOLOGICAL
            }

            // Save chosen persistent state in shared preferences
            requireContext().getSharedPreferences(Constants.SYSTEM_PREFERENCES, Context.MODE_PRIVATE)
                .edit().putInt(Constants.CREDENTIALS_ORDER, selectedValue).apply()
        }

        setTextViewColors()
        // Set the state of the dark mode switch
        val switchDarkMode: SwitchCompat = view.findViewById(R.id.switchDarkMode)
        switchDarkMode.isChecked = isDarkModeEnabled()
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                enableDarkMode()
            } else {
                disableDarkMode()
            }
        }


        binding.confirmButton.setOnClickListener {
            val old = binding.oldPw.text.toString()
            val newpw = binding.newPw.text.toString()
            val confirmnewpw = binding.confirmNewPw.text.toString()

            if(Hashing.sha256(old) != Utils.getHashedMasterPassword(requireContext())){
                Toast.makeText(
                    requireContext(),
                    getString(R.string.wrong_master_password),
                    Toast.LENGTH_LONG
                ).show()
            }
            if (old.isBlank() || newpw.isBlank() || confirmnewpw.isBlank())
            {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.blank_password) +
                    getString(R.string.passwords_cannot_be_blank),
                    Toast.LENGTH_LONG
                ).show()
            }
            else if (newpw == confirmnewpw) {
                // Actually accepts the master password
                Toast.makeText(
                    requireContext(),
                    getString(R.string.master_password_correctly_inserted),
                    Toast.LENGTH_LONG
                ).show()
                Utils.setHashedMasterPassword(requireContext(), newpw)
            }
            else
            {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.password_mismatch) +
                    getString(R.string.entered_passwords_do_not_match),
                    Toast.LENGTH_LONG
                ).show()
            }

        }

        // Inflate the layout for this fragment
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

    /**
     * Sets the text color of the TextViews based on the current mode (light or dark).
     */
    private fun setTextViewColors() {
        // Set text color for TextView 1
        val textView1: TextView = binding.tvOrderDisplay
        if (isDarkModeEnabled()) {
            // Set text color to night mode color
            textView1.setTextColor(ContextCompat.getColor(requireContext(), R.color.night_on_primary))
        } else {
            // Set text color to default color
            textView1.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }

        // Set text color for TextView 2
        val textView2: TextView = binding.tvThemeLanguage
        if (isDarkModeEnabled()) {
            // Set text color to night mode color
            textView2.setTextColor(ContextCompat.getColor(requireContext(), R.color.night_on_primary))
        } else {
            // Set text color to default color
            textView2.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }

        // Set text color for TextView 3
        val textView3: TextView = binding.tvResetMp
        if (isDarkModeEnabled()) {
            // Set text color to night mode color
            textView3.setTextColor(ContextCompat.getColor(requireContext(), R.color.night_on_primary))
        } else {
            // Set text color to default color
            textView3.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}
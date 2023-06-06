package com.project.passwordmanager.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.project.passwordmanager.R
import com.project.passwordmanager.common.Constants
import com.project.passwordmanager.common.CredentialsOrder
import com.project.passwordmanager.databinding.FragmentSettingsBinding
import com.project.passwordmanager.factories.StatsViewModelFactory
import com.project.passwordmanager.viewmodels.SettingsViewModel

class SettingsFragment : Fragment()
{
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: SettingsViewModel

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[SettingsViewModel::class.java]

        //Get the view model
        val viewModelFactory = StatsViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[SettingsViewModel::class.java]

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

        val switchDarkMode: Switch = view.findViewById(R.id.switchDarkMode)
        switchDarkMode.isChecked = isDarkModeEnabled()
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                enableDarkMode()
            } else {
                disableDarkMode()
            }
        }

        // Inflate the layout for this fragment
        return view
    }

    private fun isDarkModeEnabled(): Boolean {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }

    private fun enableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        requireActivity().recreate()
    }

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
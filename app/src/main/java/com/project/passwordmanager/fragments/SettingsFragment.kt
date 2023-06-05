package com.project.passwordmanager.fragments

import android.annotation.SuppressLint
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
import com.project.passwordmanager.databinding.FragmentSettingsBinding
import com.project.passwordmanager.factories.StatsViewModelFactory
import com.project.passwordmanager.listeners.SettingsFragmentListener
import com.project.passwordmanager.viewmodels.SettingsViewModel

class SettingsFragment : Fragment()
{
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: SettingsViewModel
    var listener: SettingsFragmentListener? = null

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[SettingsViewModel::class.java]

        //Get the view model
        val viewModelFactory = StatsViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[SettingsViewModel::class.java]

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedValue = when (checkedId) {
                R.id.radioButton -> getString(R.string.order3)
                R.id.radioButton2 -> getString(R.string.order2)
                R.id.radioButton3 -> getString(R.string.order1)
                else -> ""
            }
            listener?.onChoiceSelected(selectedValue)
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
        return inflater.inflate(R.layout.fragment_settings, container, false)
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
package com.project.passwordmanager.activities

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.project.passwordmanager.R
import com.project.passwordmanager.common.Constants
import com.project.passwordmanager.databinding.ActivityWidgetConfigurationBinding

/**
 * This activity shows the list of possible credentials to add to a widget.
 * It is the configuration activity for the CredentialsWidget.
 */
class WidgetConfigurationActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityWidgetConfigurationBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        // Set saved dark mode
        val darkMode = application.getSharedPreferences(Constants.SYSTEM_PREFERENCES, Context.MODE_PRIVATE)
            .getBoolean(Constants.DARK_MODE, false)
        if (darkMode)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        super.onCreate(savedInstanceState)
        binding = ActivityWidgetConfigurationBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        // Fully replaces the original bar with a toolbar
        binding.toolbar.title = applicationContext.getString(R.string.widget_configuration)
        setSupportActionBar(binding.toolbar)

    }
}
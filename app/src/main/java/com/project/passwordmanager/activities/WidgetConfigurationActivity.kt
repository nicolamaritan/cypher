package com.project.passwordmanager.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.passwordmanager.R
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
        super.onCreate(savedInstanceState)
        binding = ActivityWidgetConfigurationBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        // Fully replaces the original bar with a toolbar
        binding.toolbar.title = applicationContext.getString(R.string.widget_configuration)
        setSupportActionBar(binding.toolbar)

    }
}
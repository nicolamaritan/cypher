package com.project.passwordmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.passwordmanager.databinding.ActivityWidgetConfigurationBinding

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
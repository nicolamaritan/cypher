package com.project.passwordmanager

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
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

        val appWidgetId = intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID

        val resultValue = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        setResult(Activity.RESULT_CANCELED, resultValue)

        //val appWidgetManager = AppWidgetManager.getInstance(applicationContext)
        //val views = RemoteViews(applicationContext.packageName, R.layout.example_appwidget)
        //appWidgetManager.updateAppWidget(appWidgetId, views)

        binding.addWidgetButton.setOnClickListener{
            setResult(Activity.RESULT_OK, resultValue)
            finish()
        }
    }
}
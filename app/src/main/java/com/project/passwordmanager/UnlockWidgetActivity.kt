package com.project.passwordmanager

import android.appwidget.AppWidgetManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.project.passwordmanager.databinding.ActivityUnlockWidgetBinding
import com.project.passwordmanager.viewmodels.UnlockWidgetViewModel
import com.project.passwordmanager.widget.PasswordManagerWidget

/**
 * This activity is responsible for unlocking the password manager widget.
 * It provides a UI for the user to enter a password and verifies it against
 * the stored password. If the password is correct, the widget is unlocked.
 * Otherwise, an error message is displayed.
 */
class UnlockWidgetActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityUnlockWidgetBinding
    private lateinit var viewModel: UnlockWidgetViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityUnlockWidgetBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        // Fully replaces the original bar with a toolbar
        binding.toolbar.title = applicationContext.getString(R.string.unlock_widget)
        setSupportActionBar(binding.toolbar)

        viewModel = ViewModelProvider(this)[UnlockWidgetViewModel::class.java]
        viewModel.appWidgetId = intent.getIntExtra(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        )

        binding.unlockButton.setOnClickListener {

            /*
                Checks if the entered password matches the stored password in the view model.
                If the passwords match, a success message is displayed and the activity is finished,
                indicating that the widget has been unlocked. Otherwise, an error message is displayed,
                indicating that the entered password is incorrect.
             */
            if (viewModel.unlock(binding.insertedPasswordTe.text.toString()))
            {
                Toast.makeText(
                    applicationContext,
                    "Widget unlocked successfully.",
                    Toast.LENGTH_LONG
                ).show()
                PasswordManagerWidget.updateAppWidget(applicationContext, viewModel.appWidgetId)
                finish()
            }
            else
            {
                Toast.makeText(
                    applicationContext,
                    "Wrong password.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
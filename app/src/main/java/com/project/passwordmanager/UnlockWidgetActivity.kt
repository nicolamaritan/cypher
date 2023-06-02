package com.project.passwordmanager

import android.appwidget.AppWidgetManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.project.passwordmanager.databinding.ActivityUnlockWidgetBinding
import com.project.passwordmanager.security.Cryptography
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

        val encryptedPassword = intent.getStringExtra(PasswordManagerWidget.ITEM_PASSWORD)!!

        binding.credentialItem.user.text = intent.getStringExtra(PasswordManagerWidget.ITEM_USERNAME)!!
        binding.credentialItem.service.text = intent.getStringExtra(PasswordManagerWidget.ITEM_SERVICE)!!
        binding.credentialItem.password.text = applicationContext.getString(R.string.locked_password)


        binding.unlockButton.setOnClickListener {
            val insertedMasterPassword = binding.insertedMasterPasswordTe.text.toString()
            if (viewModel.unlock(insertedMasterPassword))
            {
                Toast.makeText(
                    applicationContext,
                    "Widget unlocked successfully.",
                    Toast.LENGTH_LONG
                ).show()

                // The hashes coincides, therefore can be decrypted
                binding.credentialItem.password.text = Cryptography.decryptText(
                    encryptedPassword,
                    insertedMasterPassword
                )
            }
            else
            {
                Toast.makeText(
                    applicationContext,
                    "Wrong password.",
                    Toast.LENGTH_LONG
                ).show()
            }

            binding.insertedMasterPasswordTe.text.clear()
        }
    }

    companion object
    {
        val TAG = UnlockWidgetActivity::class.java.toString()
    }
}
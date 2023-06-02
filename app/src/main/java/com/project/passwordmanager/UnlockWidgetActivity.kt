package com.project.passwordmanager

import android.appwidget.AppWidgetManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.project.passwordmanager.common.Utils
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

        // Get data from the filled-in intent
        val encryptedPassword = intent.getStringExtra(PasswordManagerWidget.ITEM_PASSWORD)!!
        binding.credentialItem.user.text = intent.getStringExtra(PasswordManagerWidget.ITEM_USERNAME)!!
        binding.credentialItem.service.text = intent.getStringExtra(PasswordManagerWidget.ITEM_SERVICE)!!
        binding.credentialItem.password.text = applicationContext.getString(R.string.locked_password)

        binding.unlockButton.setOnClickListener {
            val insertedMasterPassword = binding.insertedMasterPasswordTe.text.toString()
            if (viewModel.unlock(Utils.getHashedMasterPassword(applicationContext), insertedMasterPassword))
            {
                // The hashes coincides, therefore can be decrypted
                binding.credentialItem.password.text = Cryptography.decryptText(
                    encryptedPassword,
                    insertedMasterPassword
                )
            }

            binding.insertedMasterPasswordTe.text.clear()
        }

        // Observe the toast id to change
        viewModel.toastStringId.observe(this){
            Toast.makeText(applicationContext, getString(it), Toast.LENGTH_LONG).show()
        }
    }

    companion object
    {
        val TAG = UnlockWidgetActivity::class.java.toString()
    }
}
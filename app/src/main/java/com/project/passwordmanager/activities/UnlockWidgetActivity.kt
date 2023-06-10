package com.project.passwordmanager.activities

import android.appwidget.AppWidgetManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.project.passwordmanager.R
import com.project.passwordmanager.databinding.ActivityUnlockWidgetBinding
import com.project.passwordmanager.factories.UnlockWidgetViewModelFactory
import com.project.passwordmanager.fragments.UnlockDialogFragment
import com.project.passwordmanager.listeners.UnlockDialogListener
import com.project.passwordmanager.security.Cryptography
import com.project.passwordmanager.viewmodels.UnlockWidgetViewModel
import com.project.passwordmanager.widgets.credentials.CredentialsWidget

/**
 * This activity is responsible for unlocking the selected password.
 * It provides a UI for the user to enter a password and verifies it against
 * the stored password. If the password is correct, the password is unlocked.
 * Otherwise, an error message is displayed. This activity is invoked after
 * the user clicks on one item in the credentials Widget.
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

        // As the viewModel should not reference context, default displayed values are passed through the factory
        val viewModelFactory = UnlockWidgetViewModelFactory(
            getString(R.string.locked_password),
            getString(R.string.unlock)
        )
        viewModel = ViewModelProvider(this, viewModelFactory)[UnlockWidgetViewModel::class.java]
        viewModel.appWidgetId = intent.getIntExtra(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        )


        // Get data from the filled-in intent
        val encryptedPassword = intent.getStringExtra(CredentialsWidget.ITEM_PASSWORD)!!
        binding.credentialItem.user.text = intent.getStringExtra(CredentialsWidget.ITEM_USERNAME)!!
        binding.credentialItem.service.text = intent.getStringExtra(CredentialsWidget.ITEM_SERVICE)!!

        // Get saved state from viewModel - prevents data loss when rotating the screen
        binding.credentialItem.password.text = viewModel.displayedPassword
        binding.unlockButton.text = viewModel.displayedButtonText


        binding.unlockButton.setOnClickListener {
            Log.d(TAG, viewModel.locked.toString())
            if (viewModel.locked)
            {
                val unlockDialog = UnlockDialogFragment()

                // Set an UnlockDialogListener to handle unlock events
                unlockDialog.setUnlockDialogListener(object : UnlockDialogListener {
                    override fun onUnlockSuccess()
                    {
                        viewModel.locked = false
                        viewModel.displayedPassword = Cryptography.decryptText(encryptedPassword, unlockDialog.insertedMasterPassword)
                        viewModel.displayedButtonText = getString(R.string.lock)
                        binding.credentialItem.password.text = viewModel.displayedPassword
                        binding.unlockButton.text = viewModel.displayedButtonText
                    }

                    override fun onUnlockFailure() {}
                })

                unlockDialog.show(supportFragmentManager, UnlockDialogFragment.UNLOCK_DIALOG_FRAGMENT_TAG)
            }
            else
            {
                // If it was unlocked, pressing the button causes the credential to be locked
                viewModel.locked = true
                viewModel.displayedPassword = applicationContext.getString(R.string.locked_password)
                viewModel.displayedButtonText = applicationContext.getString(R.string.unlock)
                binding.credentialItem.password.text = viewModel.displayedPassword
                binding.unlockButton.text = viewModel.displayedButtonText
            }
        }

        binding.credentialItem.copyImageButton.setOnClickListener{
            if (!viewModel.locked)
            {
                copyPassword()
            }
            else
            {
                Toast.makeText(applicationContext, getString(R.string.unlock_the_password_first), Toast.LENGTH_SHORT).show()
            }
        }

        // Observe the toast id to change
        viewModel.toastStringId.observe(this){
            Toast.makeText(applicationContext, getString(it), Toast.LENGTH_LONG).show()
        }
    }

    private fun copyPassword()
    {
        val clipboardManager = applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Copied password", viewModel.displayedPassword)
        clipboardManager.setPrimaryClip(clipData)

        Toast.makeText(applicationContext, getString(R.string.password_copied_to_clipboard), Toast.LENGTH_SHORT).show()
    }

    companion object
    {
        private val TAG = UnlockWidgetActivity::class.java.toString()
    }
}
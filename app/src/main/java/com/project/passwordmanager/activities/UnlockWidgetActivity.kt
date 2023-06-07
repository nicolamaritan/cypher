package com.project.passwordmanager.activities

import android.appwidget.AppWidgetManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.project.passwordmanager.R
import com.project.passwordmanager.databinding.ActivityUnlockWidgetBinding
import com.project.passwordmanager.fragments.UnlockDialogFragment
import com.project.passwordmanager.listeners.UnlockDialogListener
import com.project.passwordmanager.security.Cryptography
import com.project.passwordmanager.viewmodels.UnlockWidgetViewModel
import com.project.passwordmanager.widgets.credentials.PasswordManagerWidget

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
    private var locked = true

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
            Log.d(TAG, locked.toString())
            if (locked)
            {
                val unlockDialog = UnlockDialogFragment()

                // Set an UnlockDialogListener to handle unlock events
                unlockDialog.setUnlockDialogListener(object : UnlockDialogListener {
                    override fun onUnlockSuccess()
                    {
                        locked = false
                        updatePasswordTextView(encryptedPassword, unlockDialog.insertedMasterPassword)
                    }

                    override fun onUnlockFailure() {}
                })

                unlockDialog.show(supportFragmentManager, UnlockDialogFragment.UNLOCK_DIALOG_FRAGMENT_TAG)
            }
            else
            {
                binding.credentialItem.password.text = applicationContext.getString(R.string.locked_password)
                locked = true
            }
        }

        // Observe the toast id to change
        viewModel.toastStringId.observe(this){
            Toast.makeText(applicationContext, getString(it), Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Updates the password TextView in the UI based on the provided encrypted password and master password.
     *
     * @param encryptedPassword The encrypted password to be displayed.
     * @param masterPassword The master password used for decrypting the encrypted password.
     */
    private fun updatePasswordTextView(encryptedPassword: String, masterPassword: String)
    {
        binding.credentialItem.password.text =
            if(locked) applicationContext.getString(R.string.locked_password)
            else Cryptography.decryptText(encryptedPassword, masterPassword)
    }

    companion object
    {
        private val TAG = UnlockWidgetActivity::class.java.toString()
    }
}
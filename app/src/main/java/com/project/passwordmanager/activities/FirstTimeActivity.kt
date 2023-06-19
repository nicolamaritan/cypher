package com.project.passwordmanager.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.Toast
import com.project.passwordmanager.R
import com.project.passwordmanager.common.Constants
import com.project.passwordmanager.common.Utils
import com.project.passwordmanager.databinding.ActivityFirstTimeBinding

/**
 * This activity is invoked the first time the app is used, but it is not
 * the main activity specified in the manifest. When this activity is done,
 * it launches the `MainActivity`.
 *
 * @see MainActivity
 */
class FirstTimeActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityFirstTimeBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstTimeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Fully replaces the original bar with a toolbar
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        binding.howToCreateAStrongPassword.movementMethod = LinkMovementMethod.getInstance()

        binding.button.setOnClickListener{
            val inserted = binding.insertEt.text.toString()
            val reInserted = binding.reinsertTe.text.toString()

            if (inserted.isBlank() || reInserted.isBlank())
            {
                showAlertDialog(
                    this,
                    getString(R.string.blank_password),
                    getString(R.string.passwords_cannot_be_blank)
                )
            }
            else if (inserted == reInserted)
            {
                // Actually accepts the master password
                Toast.makeText(
                    applicationContext,
                    getString(R.string.master_password_correctly_inserted),
                    Toast.LENGTH_LONG
                    ).show()

                // Next time the apps starts another activity (MainActivity) will show up
                getSharedPreferences(Constants.SYSTEM_PREFERENCES, MODE_PRIVATE)
                    .edit()
                    .putBoolean(Constants.FIRST_TIME, false)
                    .apply()

                /**
                 * The master password is never saved on clear in the database.
                 * Only the hashed value is stored, so that nobody can retrieve it.
                 * When unlocking something, the user must provide the master password.
                 * The user authenticates if the hashed provided master password
                 * matches the saved hashed value.
                 */
                Utils.setHashedMasterPassword(applicationContext, inserted)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            else
            {
                showAlertDialog(
                    this,
                    getString(R.string.password_mismatch),
                    getString(R.string.entered_passwords_do_not_match)
                )
            }
        }
    }

    /**
     * Displays an alert dialog with the provided title and message.
     *
     * @param context The context of the activity.
     * @param title The title of the alert dialog.
     * @param message The message of the alert dialog.
     */
    private fun showAlertDialog(context: Context, title: String, message: String)
    {
        val alertDialogBuilder = AlertDialog.Builder(context)

        // Set the title and message for the dialog
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage(message)

        // Set the positive button and its click listener
        alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
            // Clear the dialog if OK is clicked
            dialog.dismiss()
        }

        // Create and show the dialog
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}
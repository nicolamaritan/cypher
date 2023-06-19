package com.project.passwordmanager.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.project.passwordmanager.common.Utils
import com.project.passwordmanager.databinding.ActivityAddCredentialBinding
import com.project.passwordmanager.factories.AddCredentialActivityViewModelFactory
import com.project.passwordmanager.model.CredentialDatabase
import com.project.passwordmanager.viewmodels.AddCredentialActivityViewModel

/**
 * Activity is invoked from the AddWidget when clicking on its layout. It allows
 * the user to insert a new credential in the database.
 */
class AddCredentialActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityAddCredentialBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCredentialBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Fully replaces the original bar with a toolbar
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        // Instantiating the ViewModel
        val application = application
        val dao = CredentialDatabase.getInstance(application).credentialDao
        val viewModelFactory = AddCredentialActivityViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)[AddCredentialActivityViewModel::class.java]

        // DataBinding
        binding.credentialsDialogViewModel = viewModel
        binding.lifecycleOwner = this

        binding.addFromWidget.setOnClickListener{
            // Passes the true hashed password for the check
            if (viewModel.validateCredential() && viewModel.checkInsertedMasterPassword(Utils.getHashedMasterPassword(this)))
            {
                viewModel.addCredential()
                finish()
            }
            else
            {
                binding.insertedMasterPasswordTe.text.clear()
            }
        }

        /*
        * Observes the toastStringId in the viewModel to show
        * a toast whenever the value changes.
        * Contains the id of the string to show.
        * */
        viewModel.toastStringId.observe(this) {
            it.let {
                val toastMessage = applicationContext.getString(it)
                Toast.makeText(applicationContext, toastMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

}
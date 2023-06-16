package com.project.passwordmanager.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.project.passwordmanager.common.Utils
import com.project.passwordmanager.databinding.ActivityAddCredentialBinding
import com.project.passwordmanager.factories.AddCredentialDialogViewModelFactory
import com.project.passwordmanager.model.CredentialDatabase
import com.project.passwordmanager.viewmodels.AddCredentialDialogViewModel

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
        val viewModelFactory = AddCredentialDialogViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)[AddCredentialDialogViewModel::class.java]

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
    }

}
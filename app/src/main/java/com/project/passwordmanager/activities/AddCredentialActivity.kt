package com.project.passwordmanager.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.passwordmanager.databinding.ActivityAddCredentialBinding

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
    }
}
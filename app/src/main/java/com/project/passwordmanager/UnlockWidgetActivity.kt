package com.project.passwordmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.appbar.MaterialToolbar

class UnlockWidgetActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unlock_widget)

        // Fully replaces the original bar with a toolbar
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.title = applicationContext.getString(R.string.unlock_widget);
        setSupportActionBar(toolbar)
    }
}
package com.project.passwordmanager.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.passwordmanager.R
import com.project.passwordmanager.common.Constants

/**
 * Main Activity of the app, first displayed when it is launched. However, it is not the
 * first activity the user sees when the app is launched the first time, which it is
 * `FirstTimeActivity`. The `MainActivity` allows navigation to 3 different fragments:
 * Credentials and Statistics (bottom bar navigation) and Settings (toolbar navigation).
 *
 * @see FirstTimeActivity
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?)
    {
        val isFirstRun = getSharedPreferences(Constants.SYSTEM_PREFERENCES, MODE_PRIVATE)
            .getBoolean(Constants.FIRST_TIME, true)

        if (isFirstRun)
        {
            startActivity(Intent(this, FirstTimeActivity::class.java))

            finish()
        }

        // Set saved dark mode
        val darkMode = application.getSharedPreferences(Constants.SYSTEM_PREFERENCES, Context.MODE_PRIVATE)
            .getBoolean(Constants.DARK_MODE, false)
        if (darkMode)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)   // Used to prevent layout overlapping
                                                                                    // when writing in the edit text


        // Fully replaces the original bar with a toolbar
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Gets reference to the navigationController
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Setup bottom navigation view
        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavView.setupWithNavController(navController)

        // Builds a configuration linking the toolbar to the navigation graph
        val builder = AppBarConfiguration.Builder(navController.graph)
        val appBarConfiguration = builder.build()
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        /*
        * Actually adds elements to the toolbar.
        * called when the activity is ready to add items to the toolbar.
        * */
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        /*
        * This method runs whenever an item in the toolbar is clicked.
        * It is used to respond to clicks.
        * */
        val navController = findNavController(R.id.nav_host_fragment)

        // If the navController is unable to navigate, invokes superclass default method.
        return item.onNavDestinationSelected(navController) ||
                super.onOptionsItemSelected(item)
    }

}
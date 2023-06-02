package com.project.passwordmanager

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.passwordmanager.common.Constants


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?)
    {
        /*getSharedPreferences(Constants.SYSTEM_PREFERENCES, MODE_PRIVATE)
            .edit()
            .putBoolean(Constants.FIRST_TIME, true)
            .apply()


         */
        val isFirstRun = getSharedPreferences(Constants.SYSTEM_PREFERENCES, MODE_PRIVATE)
            .getBoolean(Constants.FIRST_TIME, true)

        if (isFirstRun)
        {
            startActivity(Intent(this, FirstTimeActivity::class.java))

            finish()
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
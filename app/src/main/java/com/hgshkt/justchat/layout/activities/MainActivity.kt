package com.hgshkt.justchat.layout.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.hgshkt.justchat.R
import com.hgshkt.justchat.auth.AppAuth

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        val auth = AppAuth()

        if (auth.entered) {
            navigationView.setupWithNavController(navController)
            appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

            setupActionBarWithNavController(navController, appBarConfiguration)
        } else {
            Intent(applicationContext, LoginActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)

        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun init() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        navController = navHostFragment!!.findNavController()
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)
    }
}
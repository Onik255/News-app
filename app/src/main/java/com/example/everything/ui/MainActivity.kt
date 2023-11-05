package com.example.everything.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.everything.R
import com.example.everything.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment_container
        ) as NavHostFragment

        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setupWithNavController(navController)
    }
}
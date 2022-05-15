package com.example.androidmvvmcleanarchitectureexample.ui.entryflow.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.example.androidmvvmcleanarchitectureexample.R
import com.example.common.utils.extentions.setStatusBarColorAnyVersion

class LoginActivity : AppCompatActivity() {
    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        navController.addOnDestinationChangedListener { _: NavController, nd: NavDestination, _: Bundle? ->
            setWindowDecoration(nd.id)
        }
    }

    private fun setWindowDecoration(id: Int) {
        val color: Int = when (id) {
            R.id.splashFragment -> {
                R.color.background_splash
            }
            R.id.onBoardingFragment -> {
                R.color.background_on_boarding_section
            }
            else -> R.color.blue_primary
        }

        window.setStatusBarColorAnyVersion(color)
    }
}
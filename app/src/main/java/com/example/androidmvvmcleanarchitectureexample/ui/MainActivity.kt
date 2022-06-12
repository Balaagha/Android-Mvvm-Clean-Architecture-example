package com.example.androidmvvmcleanarchitectureexample.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.androidmvvmcleanarchitectureexample.R
import com.example.core.view.BaseActivity
import com.example.uitoolkit.loading.LoadingPopup
import com.example.uitoolkit.loading.LoadingPopup.Companion.getInstance
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val loadingDialog: LoadingPopup? by lazy {
        getInstance(this).customLayoutLoading()
            .setCustomViewID(R.layout.dialog_custom_image_loading_popup,
                android.R.color.background_dark)
            .noIntentionalDelay()
            .setBackgroundOpacity(40)
            .cancelable(false)
            .setCustomizationBlock { context, rootView ->
                Glide.with(context).load(R.drawable.ios_loading)
                    .into(rootView.findViewById(R.id.gifImageView))
            }
            .build()
    }

    private lateinit var navController: NavController

    override fun showLoadingDialog(): Boolean {
        return LoadingPopup.showLoadingPopUp(loadingDialog)
    }

    override fun hideLoadingDialog(): Boolean {
        return LoadingPopup.hideLoadingPopUp(loadingDialog)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
//        navController = navHostFragment.navController
//
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.recipesFragment,
//                R.id.favoriteRecipesFragment,
//                R.id.foodJokeFragment
//            )
//        )
//
//        bottomNavigationView.setupWithNavController(navController)
//
//        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}
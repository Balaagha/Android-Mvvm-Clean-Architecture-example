package com.example.androidmvvmcleanarchitectureexample.ui.entryflow.activity

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.example.androidmvvmcleanarchitectureexample.R
import com.example.uitoolkit.utils.extentions.setStatusBarColorAnyVersion
import com.example.core.view.BaseActivity
import com.example.uitoolkit.loading.LoadingPopup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }
    private val loadingDialog: LoadingPopup? by lazy {
        LoadingPopup.getInstance(this).customLayoutLoading()
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

    override fun showLoadingDialog(): Boolean {
        return LoadingPopup.showLoadingPopUp(loadingDialog)
    }

    override fun hideLoadingDialog(): Boolean {
        return LoadingPopup.hideLoadingPopUp(loadingDialog)
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
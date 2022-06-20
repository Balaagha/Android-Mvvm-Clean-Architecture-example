package com.example.androidmvvmcleanarchitectureexample.ui

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.example.androidmvvmcleanarchitectureexample.R
import com.example.common.utils.extentions.setupWithNavController
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

    lateinit var navController: LiveData<NavController>

    override fun showLoadingDialog(): Boolean {
        return LoadingPopup.showLoadingPopUp(loadingDialog)
    }

    override fun hideLoadingDialog(): Boolean {
        return LoadingPopup.hideLoadingPopUp(loadingDialog)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        setUpBottomNavigationWithGraphs()
    }

    private fun setUpBottomNavigationWithGraphs() {
        val graphIds = listOf(
            R.navigation.nav_home,
            R.navigation.nav_home,
            R.navigation.nav_home,
            R.navigation.nav_home,
        )

        val controller = bottomNavigationView.setupWithNavController(
            graphIds,
            supportFragmentManager,
            R.id.fragment_host_container,
            intent
        )

        navController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.value?.navigateUp()!! || super.onSupportNavigateUp()
    }

}
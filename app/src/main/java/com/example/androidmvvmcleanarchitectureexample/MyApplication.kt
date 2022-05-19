package com.example.androidmvvmcleanarchitectureexample

import android.app.Application
import androidx.core.app.NotificationManagerCompat
import com.example.androidmvvmcleanarchitectureexample.helper.NotificationHelper
import com.example.data.base.commonimpl.NetworkStatusListenerHelper
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication: Application() {
    @Inject
    lateinit var networkStatusListenerHelper: NetworkStatusListenerHelper

    override fun onCreate() {
        super.onCreate()
        NotificationHelper.createNotificationChannel(this,
            NotificationManagerCompat.IMPORTANCE_DEFAULT, false,
            getString(R.string.app_name), "App notification channel.")

        networkStatusListenerHelper.init()
    }
}
package com.example.androidmvvmcleanarchitectureexample

import android.app.Application
import com.example.androidmvvmcleanarchitectureexample.util.helper.NetworkStatusListenerHelper
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication: Application() {
    @Inject
    lateinit var networkStatusListenerHelper: NetworkStatusListenerHelper

    override fun onCreate() {
        super.onCreate()
        networkStatusListenerHelper.init()
    }
}
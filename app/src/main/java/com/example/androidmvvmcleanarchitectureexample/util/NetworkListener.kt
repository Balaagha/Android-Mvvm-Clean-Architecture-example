package com.example.androidmvvmcleanarchitectureexample.util

import android.content.Context
import android.net.*
import android.os.Build
import android.util.Log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService



class NetworkListener : ConnectivityManager.NetworkCallback() {

    private val isNetworkAvailable = MutableStateFlow(false)

    fun checkNetworkAvailability(context: Context): MutableStateFlow<Boolean> {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(this)
        } else {
            /**
             * Automatically start a download once an internet connection is established
             * could filter using .addCapability(int) or .addTransportType(int) on Builder [networkChangeFilterBuilder]
             */
            val networkChangeFilterBuilder = NetworkRequest.Builder()
            val networkChangeFilter = networkChangeFilterBuilder.build()

            connectivityManager.registerNetworkCallback(networkChangeFilter, this)
        }

        var isConnected = false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            val network = connectivityManager.activeNetwork

            val networkCapability = connectivityManager.getNetworkCapabilities(network)
            networkCapability?.let {
                if(it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                    isConnected = true
                }
            }
        } else {
            connectivityManager.allNetworks.forEach { network ->
                val networkCapability = connectivityManager.getNetworkCapabilities(network)
                networkCapability?.let {
                    if(it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                        isConnected = true
                        return@forEach
                    }
                }
            }
        }



        isNetworkAvailable.value = isConnected

        return isNetworkAvailable
    }

    override fun onAvailable(network: Network) {
        Log.d("myTag","call onAvailable")
        isNetworkAvailable.value = true
    }

    override fun onLost(network: Network) {
        Log.d("myTag","call onLost")
        isNetworkAvailable.value = false
    }
}
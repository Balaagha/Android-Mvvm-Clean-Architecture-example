package com.example.androidmvvmcleanarchitectureexample.di

import com.example.androidmvvmcleanarchitectureexample.util.helper.NetworkStatusListenerHelperImpl
import com.example.common.commonimpl.NetworkStatusListenerHelper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppBindModule {

    @Binds
    @Singleton
    abstract fun bindNetworkStatusListenerHelper(configImpl: NetworkStatusListenerHelperImpl): NetworkStatusListenerHelper

}

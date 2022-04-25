package com.example.data.di

import com.example.data.features.recipes.services.RecipesServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
open class DataModule {

    companion object {
        const val BASE_URL = "https://api.spoonacular.com"
        const val BASE_IMAGE_URL = "https://spoonacular.com/cdn/ingredients_100x100/"
        const val API_KEY = "121e0c6ca2b047ea96baf3c27c86cefa"
    }

    // retrofit region start

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {

//        val client = OkHttpClient.Builder().apply {
//            readTimeout(15, TimeUnit.SECONDS)
//            connectTimeout(15, TimeUnit.SECONDS)
//            addInterceptor(httpLoggingInterceptor)
//        }
        val client = OkHttpClient.Builder()
        client.addInterceptor(httpLoggingInterceptor)

        client.readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)

        return client.build()
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // retrofit region end


    // services region start

    @Singleton
    @Provides
    fun provideRecipesServices(
        retrofit: Retrofit,
    ): RecipesServices {
        return retrofit.create(RecipesServices::class.java)
    }

    // services region end


}
package com.example.androidmvvmcleanarchitectureexample.di

import android.content.Context
import androidx.room.Room
import com.example.androidmvvmcleanarchitectureexample.data.database.AppDatabase
import com.example.androidmvvmcleanarchitectureexample.data.database.common.DatabaseConstant.DATABASE_NAME
import com.example.androidmvvmcleanarchitectureexample.data.database.receiptui.RecipesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideRecipesDao(
        database: AppDatabase,
    ): RecipesDao {
        return database.recipesDao()
    }

    @Singleton
    @Provides
    fun provideContactListDao(
        database: AppDatabase,
    ) = database.contactListDao()

}
package com.example.data.di

import com.example.data.features.recipes.repository.RecipesRepository
import com.example.data.features.recipes.repository.RecipesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class BindModule {

    @Singleton
    @Binds
    abstract fun bindRecipesRepository(repo: RecipesRepositoryImpl): RecipesRepository

}
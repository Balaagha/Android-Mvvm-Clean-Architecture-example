package com.example.androidmvvmcleanarchitectureexample.data

import com.example.androidmvvmcleanarchitectureexample.data.database.receiptui.RecipesDao
import com.example.androidmvvmcleanarchitectureexample.data.database.receiptui.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {

    fun readDatabase(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }

    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }

}
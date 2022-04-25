package com.example.data.database.feature.recipes.repository

import com.example.data.database.feature.recipes.dao.RecipesDao
import com.example.data.database.feature.recipes.model.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReceiptDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {

    fun getReceiptListData(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }

    suspend fun insertRecipesList(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }

}
package com.example.data.features.recipes.repository

import android.content.Context
import android.util.Log
import com.example.data.base.models.DataWrapper
import com.example.data.base.models.RequestWrapper
import com.example.data.base.repository.BaseRepository
import com.example.data.features.recipes.models.FoodRecipe
import com.example.data.features.recipes.services.RecipesServices
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

interface RecipesRepository {

    suspend fun getRecipes(requestData: RequestWrapper<Map<String, String>>): DataWrapper<Response<FoodRecipe>>

    suspend fun searchRecipes(requestData: RequestWrapper<Map<String, String>>): DataWrapper<Response<FoodRecipe>>

}

@Singleton
class RecipesRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val recipesServices: RecipesServices,
) : BaseRepository(context),RecipesRepository {

    override suspend fun getRecipes(requestData: RequestWrapper<Map<String, String>>): DataWrapper<Response<FoodRecipe>> {
        return safeApiCall {
            recipesServices.getRecipes(requestData.requestValue)
        }
    }

    override suspend fun searchRecipes(requestData: RequestWrapper<Map<String, String>>): DataWrapper <Response<FoodRecipe>> {
        return safeApiCall {
            recipesServices.searchRecipes(requestData.requestValue)
        }
    }

}
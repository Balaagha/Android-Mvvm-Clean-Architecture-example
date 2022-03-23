package com.example.androidmvvmcleanarchitectureexample.data

import com.example.data.features.recipes.models.FoodRecipe
import com.example.data.features.recipes.services.RecipesServices
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val recipesServicesApi: RecipesServices
) {

    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return recipesServicesApi.getRecipes(queries)
    }

    suspend fun searchRecipes(searchQuery: Map<String, String>): Response<FoodRecipe> {
        return recipesServicesApi.searchRecipes(searchQuery)
    }


}
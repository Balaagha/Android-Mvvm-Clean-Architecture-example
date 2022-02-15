package com.example.androidmvvmcleanarchitectureexample.models

import com.google.gson.annotations.SerializedName

data class FoodRecipe(
    @SerializedName("results")
    val recipesResults: List<RecipesResult>
)
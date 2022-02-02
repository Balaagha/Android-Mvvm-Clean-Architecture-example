package com.example.androidmvvmcleanarchitectureexample.models

import com.example.androidmvvmcleanarchitectureexample.models.Result
import com.google.gson.annotations.SerializedName

data class FoodRecipe(
    @SerializedName("results")
    val results: List<Result>
)
package com.example.data.features.entryflow.services

import com.example.data.features.entryflow.models.responces.LoginResponse
import com.example.data.features.recipes.models.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.QueryMap

interface EntryFlowServices {

    @GET("/beso-asanlogin-ms/login")
    suspend fun login(
        @QueryMap queries: HashMap<String, Any>,
        @HeaderMap headers: HashMap<String, Any>
    ): Response<LoginResponse>

    @GET("/beso-asanlogin-ms/login")
    suspend fun searchRecipes(
        @QueryMap queries: HashMap<String, Any>,
        @HeaderMap headers: HashMap<String, Any>
    ): Response<FoodRecipe>

}
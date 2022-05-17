package com.example.data.features.entryflow.services

import com.example.data.base.models.ModelWrapper
import com.example.data.features.entryflow.models.responces.LoginResponse
import retrofit2.Response
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface EntryFlowServices {

    @POST("/beso-asanlogin-ms/login")
    suspend fun login(
        @QueryMap queries: HashMap<String, String>,
        @HeaderMap headers: HashMap<String, String>
    ): Response<ModelWrapper<LoginResponse>>

}

package com.example.data.features.entryflow.services

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
    ): Response<LoginResponse>

}

//  http://ec2-3-15-37-163.us-east-2.compute.amazonaws.com/beso-asanlogin-ms/login?lang=AZ
//  http://ec2-3-15-37-163.us-east-2.compute.amazonaws.com/beso-asanlogin-ms/login?lang=AZ
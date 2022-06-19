package com.example.data.features.entryflow.models.responces


import com.google.gson.annotations.SerializedName

data class LoginRegisterResponse(
    @SerializedName("authToken")
    var authToken: String?
)
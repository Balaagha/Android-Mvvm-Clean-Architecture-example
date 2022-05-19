package com.example.data.features.entryflow.models.request.register

import com.google.gson.annotations.SerializedName


data class RegisterRequestData(
    @SerializedName("businessData")
    var businessData: BusinessData? = null,
    @SerializedName("contractData")
    var contractData: ContractData? = null,
    @SerializedName("userData")
    var userData: UserData? = null
)
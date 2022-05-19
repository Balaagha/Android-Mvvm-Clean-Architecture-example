package com.example.data.features.entryflow.models.request.register


import com.google.gson.annotations.SerializedName

data class ContractData(
    @SerializedName("locationData")
    var locationData: LocationData? = null,
    @SerializedName("mail")
    var mail: String? = null,
    @SerializedName("phoneNumber")
    var phoneNumber: String? = null
)
package com.example.data.features.entryflow.models.request.register

import com.google.gson.annotations.SerializedName


data class BusinessData(
    @SerializedName("businessAddress")
    var businessAddress: String? = null,
    @SerializedName("businessMailAddress")
    var businessMailAddress: String? = null,
    @SerializedName("businessName")
    var businessName: String? = null,
    @SerializedName("businessRegistrationNumber")
    var businessRegistrationNumber: String? = null,
    @SerializedName("businessVatNumber")
    var businessVatNumber: String? = null
)
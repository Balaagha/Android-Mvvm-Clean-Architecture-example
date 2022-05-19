package com.example.data.features.entryflow.models.request.register


import com.google.gson.annotations.SerializedName

data class LocationData(
    @SerializedName("country")
    var country: String? = null,
    @SerializedName("streetName")
    var streetName: String? = null,
    @SerializedName("streetNumber")
    var streetNumber: String? = null
)
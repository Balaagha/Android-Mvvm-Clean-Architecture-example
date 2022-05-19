package com.example.data.features.entryflow.models.request.register


import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("birthDate")
    var birthDate: String? = null,
    @SerializedName("firstName")
    var firstName: String? = null,
    @SerializedName("lastName")
    var lastName: String? = null,
    @SerializedName("middleName")
    var middleName: String? = null,
    @SerializedName("password")
    var password: String? = null,
    @SerializedName("userName")
    var userName: String? = null
)
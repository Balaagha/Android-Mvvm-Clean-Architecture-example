package com.example.data.base.models

import com.google.gson.annotations.SerializedName


data class ModelWrapper<T> constructor(
  @SerializedName("message")
  val message: String? = null,
  @SerializedName("data")
  val data: T? = null
)
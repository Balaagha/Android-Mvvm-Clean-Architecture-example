package com.example.data.base.models

import androidx.annotation.StringRes



sealed class DataWrapper<out T>(private val value: T? = null) {

  open operator fun invoke(): T? = value

  data class Success<out T>(val value: T) : DataWrapper<T>(value)

  data class Failure <out T>(
    val failureType: FailureType,
    val failureBehavior: FailureBehavior = FailureBehavior.SILENT,
    val code: Int? = null,
    val message: String? = null,
    @StringRes val messageRes: Int? = null,
  ) : DataWrapper<T>()

  object Loading : DataWrapper<Nothing>()
  object Uninitialized : DataWrapper<Nothing>()
}
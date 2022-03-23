package com.example.data.base.repository

import android.content.Context
import com.example.data.base.commonimpl.NetworkStatusListenerHelper
import com.example.data.base.models.ErrorResponse
import com.example.data.base.models.FailureType
import com.example.data.base.models.DataWrapper
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseRepository(
    private val appContext: Context
) {
    lateinit var networkStatusListenerHelper: NetworkStatusListenerHelper

    init {
        val entryPoint =
            EntryPointAccessors.fromApplication(appContext, BaseRepositoryEntryPoint::class.java)
        networkStatusListenerHelper = entryPoint.networkStatusListenerHelper()

    }

    @InstallIn(SingletonComponent::class)
    @EntryPoint
    interface BaseRepositoryEntryPoint {
        fun networkStatusListenerHelper(): NetworkStatusListenerHelper
    }

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): DataWrapper<Response<T>> {
        return try {
            if(networkStatusListenerHelper.getNetworkAvailabilityStatus()){
                DataWrapper.Failure(FailureType.NO_INTERNET_CONNECTION)
            } else {
                DataWrapper.Success(
                    value = apiCall.invoke()
                )
            }
        } catch (throwable: Throwable) {
            handleApiCallException(throwable)
        }
    }

    private fun <T> handleApiCallException(throwable: Throwable): DataWrapper<T> {
        when (throwable) {
            is SocketTimeoutException -> {
                return DataWrapper.Failure(FailureType.RESPONSE_TIMEOUT)
            }
            is UnknownHostException -> {
                return DataWrapper.Failure(FailureType.NO_INTERNET_CONNECTION)
            }

            is ConnectException -> {
                return DataWrapper.Failure(FailureType.NO_INTERNET_CONNECTION)
            }
            is HttpException -> {
                when {
                    throwable.code() == 401 -> {
                        val errorResponse = Gson().fromJson(
                            throwable.response()?.errorBody()!!.charStream().readText(),
                            ErrorResponse::class.java
                        )
                        return DataWrapper.Failure(
                            failureType = FailureType.HTTP_EXCEPTION,
                            code = throwable.code(),
                            message = errorResponse.detail
                        )
                    }
                    else -> {
                        return if (throwable.response()?.errorBody()!!.charStream().readText().isEmpty()) {
                            DataWrapper.Failure(
                                failureType = FailureType.HTTP_EXCEPTION,
                                code = throwable.code()
                            )
                        } else {
                            try {
                                val errorResponse = Gson().fromJson(
                                    throwable.response()?.errorBody()!!.charStream().readText(),
                                    ErrorResponse::class.java
                                )

                                DataWrapper.Failure(
                                    failureType = FailureType.HTTP_EXCEPTION,
                                    code = throwable.code(),
                                    message = errorResponse.detail
                                )
                            } catch (ex: JsonSyntaxException) {
                                DataWrapper.Failure(
                                    failureType = FailureType.HTTP_EXCEPTION,
                                    code = throwable.code()
                                )
                            }
                        }
                    }
                }
            }
            else -> {
                return DataWrapper.Failure(FailureType.OTHER)
            }
        }
    }


}
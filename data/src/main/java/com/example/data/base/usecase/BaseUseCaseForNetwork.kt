package com.example.data.base.usecase

import com.example.data.R
import com.example.data.base.models.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.cancel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.Response
import java.util.concurrent.CancellationException
import java.util.concurrent.atomic.AtomicBoolean


abstract class BaseUseCaseForNetwork <ResponseType, in RequestParams> {

    open val customHeaders = HashMap<String, String>()

    fun withHeader(headers: HashMap<String, String>): BaseUseCaseForNetwork<ResponseType, RequestParams> {
        this.customHeaders.putAll(headers)
        return this
    }

    /**
     * [isCurrentUseCaseBusy] is responsible for detect current use case have progress operation
     * if it is true, [executeAsFlow] function cancel operation
     */
    private var isCurrentUseCaseBusy: AtomicBoolean = AtomicBoolean(false)

    open var isShowBaseLoadingIndicator: Boolean = false

    internal abstract suspend fun run(params: RequestParams): DataWrapper<Response<ModelWrapper<ResponseType>>>

    suspend operator fun invoke(params: RequestParams): DataWrapper<ResponseType> {
        return parseResult(run(params))
    }

    /**
     * It is for reactive execution. Firstly flow return Loading state
     * After return Loading state, use case check api response, and return Failure or Success response
     * [isCurrentUseCaseBusy] is guaranteed use not have progress operation
     * [parseResult] is generic class for handle response. If you have other logic, you can override it your own use case
     */
    fun invokeAsFlow(
        params: RequestParams
    ): Flow<DataWrapper<ResponseType>> = flow {
        if (isCurrentUseCaseBusy.get()) {
            currentCoroutineContext().cancel(CancellationException(exceptionMessage))
            return@flow
        }
        isCurrentUseCaseBusy.set(true)
        try {
            // Emit Loading State
            emit(DataWrapper.Loading)

            // Execute api call
            val apiResult = run(params)
            val parsedResult = parseResult(apiResult)

            // Emit parsed api result
            emit(parsedResult)

            if(parsedResult is DataWrapper.Success<*> || parsedResult is DataWrapper.Failure){
                isCurrentUseCaseBusy.set(false)
            }
        } catch (ex: Exception) {
            emit(
                DataWrapper.Failure(
                    failureType = FailureType.OTHER,
                    failureBehavior = FailureBehavior.ALERT,
                    messageRes = R.string.base_generic_error
                )
            )
            isCurrentUseCaseBusy.set(false)
        }
    }

    open fun parseResult(data: DataWrapper<Response<ModelWrapper<ResponseType>>>): DataWrapper<ResponseType> {
        return when (data) {
            is DataWrapper.Success -> {
                when(val code = data.invoke()?.code()) {
                     in 200 .. 299 -> {
                        when (val resultBody = data.value.body()?.data) {
                            null -> {
                                DataWrapper.Failure(
                                    failureType = FailureType.EMPTY_OR_NULL_RESULT,
                                    failureBehavior = FailureBehavior.SILENT,
                                    code = code
                                )
                            }
                            is List<*> -> {
                                if ((resultBody as List<*>).isNotEmpty()) {
                                    DataWrapper.Success(resultBody)
                                } else {
                                    DataWrapper.Failure(FailureType.EMPTY_OR_NULL_RESULT)
                                }
                            }
                            else -> {
                                DataWrapper.Success(resultBody)
                            }
                        }
                    }
                    in 300 .. 399 -> {
                        DataWrapper.Failure(
                            failureType = FailureType.API_REDIRECTION_CODE_RESPONSE,
                            code = data.value.code(),
                            message = data.value.errorBody()?.string(),
                            failureBehavior = FailureBehavior.SILENT
                        )
                    }
                    else -> {
                        if (code == 401) {
                            DataWrapper.Failure(
                                failureType = FailureType.AUTH_TOKEN_EXPIRED,
                                code = data.value.code(),
                                message = data.value.errorBody()?.string(),
                                failureBehavior = FailureBehavior.SILENT
                            )
                        } else {
                            val errorResponse: ApiErrorResponseModel? = parseErrorBody(data.value.errorBody())

                            DataWrapper.Failure(
                                failureType = FailureType.API_GENERIC_ERROR,
                                code = data.value.code(),
                                title = errorResponse?.error,
                                message = errorResponse?.errorDetails,
                                messageRes = if(errorResponse?.error == null && errorResponse?.errorDetails == null) R.string.base_generic_error else null,
                                failureBehavior = FailureBehavior.ALERT
                            )
                        }
                    }
                }

            }
            is DataWrapper.Failure -> {
                when (data.failureType) {
                    FailureType.NO_INTERNET_CONNECTION -> {
                        return DataWrapper.Failure(
                            failureType = FailureType.NO_INTERNET_CONNECTION,
                            failureBehavior = FailureBehavior.ALERT,
                            messageRes = R.string.base_no_internet_connection,
                        )
                    }
                    FailureType.RESPONSE_TIMEOUT -> {
                        return DataWrapper.Failure(
                            failureType = FailureType.RESPONSE_TIMEOUT,
                            failureBehavior = FailureBehavior.ALERT,
                            messageRes = R.string.base_operation_take_long_time
                        )
                    }
                    else -> DataWrapper.Failure(
                        failureType = data.failureType,
                        failureBehavior = FailureBehavior.ALERT,
                        messageRes = R.string.base_generic_error
                    )
                }
            }
            else -> {
                DataWrapper.Failure(
                    failureType = FailureType.OTHER,
                    failureBehavior = FailureBehavior.ALERT,
                    messageRes = R.string.base_generic_error
                )
            }
        }
    }

    open fun parseErrorBody(responseBody: ResponseBody?): ApiErrorResponseModel? {
        return responseBody?.let { errorResponseBody ->
            Gson().fromJson(
                errorResponseBody.charStream(),
                object : TypeToken<ApiErrorResponseModel>() {}.type
            )
        }
    }

    protected val <T> T.asRequestWrapper: RequestWrapper<T>
        get() {
            return RequestWrapper(
                requestValue = this,
                customHeaders = this@BaseUseCaseForNetwork.customHeaders
            )
        }

    companion object {
        const val exceptionMessage = "UseCase in use"
    }

}
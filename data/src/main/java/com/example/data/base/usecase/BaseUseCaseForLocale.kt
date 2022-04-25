package com.example.data.base.usecase

import com.example.data.base.models.DataWrapper
import com.example.data.base.models.FailureType
import kotlinx.coroutines.cancel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.CancellationException
import java.util.concurrent.atomic.AtomicBoolean

abstract class BaseUseCaseForLocale <ResponseType, in RequestParams> where ResponseType : Any {

    /**
     * [isCurrentUseCaseBusy] is responsible for detect current use case have progress operation
     * if it is true, [executeAsFlow] function cancel operation
     */
    private var isCurrentUseCaseBusy: AtomicBoolean = AtomicBoolean(false)

    open var isShowBaseLoadingIndicator: Boolean = false

    internal abstract suspend fun run(params: RequestParams): ResponseType

    suspend operator fun invoke(params: RequestParams): ResponseType {
        return parseResult(run(params))
    }

    /**
     * It is for reactive execution. Firstly flow return Loading state
     * [isCurrentUseCaseBusy] is guaranteed use not have progress operation
     * [parseResultAsDataWrapper] is generic class for handle response. If you have other logic, you can override it your own use case
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

            // Execute
            val parsedResult = parseResultAsDataWrapper(run(params))

            // Emit parsed api result
            emit(parsedResult)

            if(parsedResult is DataWrapper.Success<*> || parsedResult is DataWrapper.Failure){
                isCurrentUseCaseBusy.set(false)
            }
        } catch (ex: Exception) {
            emit(
                DataWrapper.Failure(
                    failureType = FailureType.OTHER,
                )
            )
            isCurrentUseCaseBusy.set(false)
        }
    }

    open fun parseResult(result: ResponseType): ResponseType {
        return result
    }

    open fun parseResultAsDataWrapper(result: ResponseType): DataWrapper<ResponseType> {
        return DataWrapper.Success(result)
    }


    companion object {
        const val exceptionMessage = "UseCase in use"
    }

}
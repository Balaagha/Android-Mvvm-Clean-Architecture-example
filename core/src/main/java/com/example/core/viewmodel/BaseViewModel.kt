package com.example.core.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.base.models.DataWrapper
import com.example.data.base.models.FailureBehavior
import com.example.data.base.usecase.BaseUseCaseForNetwork
import com.example.common.utils.helper.SingleLiveEvent
import com.example.core.event.BaseUiEvent
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

open class BaseViewModel(
    val savedStateHandle: SavedStateHandle?,
    application: Application
) : AndroidViewModel(application) {

    /**
     * For triggering base events on UI
     */
    val event: SingleLiveEvent<BaseUiEvent> =
        SingleLiveEvent()

    /**
     * Top level exception handler for the included context
     */
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
//        Timber.e(throwable)
    }

    protected fun <T, R> BaseUseCaseForNetwork<T, R>.execute(
        params: R,
        successOperation: ((value: DataWrapper<T>) -> Unit)? = null,
        failOperation: ((value: DataWrapper<T>) -> Unit)? = null,
        block: ((value: DataWrapper<T>) -> Unit)? = null
    ) {
        launchSafe {
            this@execute.invokeAsFlow(params).collect {
                if (isShowBaseLoadingIndicator) {
                    event.postValue(BaseUiEvent.LoadingIndicator(it is DataWrapper.Loading))
                }
                when(it){
                    is DataWrapper.Success ->{
                        successOperation?.invoke(it)
                    }
                    is DataWrapper.Failure ->{
                        when (it.failureBehavior){
                            FailureBehavior.ALERT -> {
                                event.postValue(BaseUiEvent.Alert())
                            }
                            FailureBehavior.SNACK_BAR -> {
                                event.postValue(BaseUiEvent.SnackBar())
                            }
                            FailureBehavior.TOAST -> {
                                event.postValue(BaseUiEvent.Toast())
                            }
                        }
                        failOperation?.invoke(it)
                    }
                }

                block?.invoke(it)
            }
        }
    }

    protected fun launchSafe(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch(context + exceptionHandler, start, block)
    }


}
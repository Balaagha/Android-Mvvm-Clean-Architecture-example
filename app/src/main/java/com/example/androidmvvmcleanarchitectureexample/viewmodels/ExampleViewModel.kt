package com.example.androidmvvmcleanarchitectureexample.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import javax.inject.Inject

@HiltViewModel
class ExampleViewModel @Inject constructor(
    application: Application,
) : AndroidViewModel(application) {

    val userId = getSelectedUserId().map {
        userId -> "id-> $userId"
    }


    private fun getSelectedUserId(): Flow<Int> {
        var value = 1
        return flow {
            while (true){
                delay(500)
                emit(value)
                value++
            }
        }
    }

    private fun getUserData(userId: String): Flow<String> {
        return flow {
            emit("data for selected user id($userId)")
        }
    }

    @ExperimentalCoroutinesApi
    val result: StateFlow<String> = userId.flatMapLatest { newUserId ->
        getUserData(newUserId)
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = "No Data"
    )

}
package com.example.androidmvvmcleanarchitectureexample.viewmodels

import android.util.Log
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

object ExampleObject {
    var checker = 1

    fun getSelectedUserId(): Flow<Int> {
        var value = 1
        return flow {
            while (true){
                delay(1500)
                emit(value)
                value++
                Log.d("myTagExample","@getSelectedUserId() value is increase ($value)")
            }
        }
    }

    fun getUserDataByUserId(userId: String): Flow<String> {
        return flow {
            emit("data for selected user id($userId)")
        }
    }

    @ExperimentalCoroutinesApi
    val resultNew: StateFlow<String> =  flow {
        var value = 1
        while (true){
            delay(1000)
            emit("resultNew is $value")
            value++
            Log.d("myTagExample","resultNew value is increase ($value)")
        }
    }.stateIn(
        scope = ProcessLifecycleOwner.get().lifecycleScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = "No Data"
    )



}
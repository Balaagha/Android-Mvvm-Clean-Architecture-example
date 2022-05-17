package com.example.androidmvvmcleanarchitectureexample.ui.entryflow.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.androidmvvmcleanarchitectureexample.ui.entryflow.models.login.UserLoginData
import com.example.core.viewmodel.BaseViewModel
import com.example.data.features.entryflow.models.request.LoginRequest
import com.example.data.features.entryflow.usecases.LoginUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EntryViewModel @Inject constructor(
    var loginUserUseCase: LoginUserUseCase,
    application: Application,
    savedStateHandle: SavedStateHandle
) : BaseViewModel(savedStateHandle, application) {

    var userData: UserLoginData = UserLoginData()

    fun onSignInBtnClicked() {
        loginUserUseCase.execute(
            LoginRequest(
                username = "balaagha13",
                password = "13081994b",
            ),
            successOperation = {
                Log.d("myTagRequest","${it.invoke()?.authToken}")
            }
        )
    }

    val email = MutableLiveData("213")
    val password = MutableLiveData("3123")

    companion object {

    }

}

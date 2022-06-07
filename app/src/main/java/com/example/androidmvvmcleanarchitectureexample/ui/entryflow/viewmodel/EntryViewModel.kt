package com.example.androidmvvmcleanarchitectureexample.ui.entryflow.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.androidmvvmcleanarchitectureexample.ui.entryflow.models.login.UserLoginData
import com.example.androidmvvmcleanarchitectureexample.ui.entryflow.models.register.UserRegisterData
import com.example.androidmvvmcleanarchitectureexample.ui.entryflow.view.login.LoginFragment
import com.example.common.utils.helper.SingleLiveEvent
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

    /**
     * this field contains two type of data
     * first - class which if identify your current class is valid for navigation
     * second - root id which navigate your true destination
     * implementation in viewModel => navigationRouteId.postValue( Fragment::class.java to R.id.action_id )
     *
     */
    val navigationRouteId = SingleLiveEvent<Pair<Class<*>, Int>>()

    var userLoginData: UserLoginData = UserLoginData()
    var userRegisterData: UserRegisterData = UserRegisterData()

    fun onSignInBtnClicked() {
        loginUserUseCase.execute(
            LoginRequest(
                username = userLoginData.userName.get(),
                password = userLoginData.userPassword.get(),
            ),
            successOperation = {
                navigationRouteId.postValue(
                    LoginFragment::class.java to ZERO
                )
                Log.d("myTagRequest","${it.invoke()?.authToken}")
            }
        )
    }

    val email = MutableLiveData("213")
    val password = MutableLiveData("3123")

    companion object {
        const val ZERO = 0
    }

}

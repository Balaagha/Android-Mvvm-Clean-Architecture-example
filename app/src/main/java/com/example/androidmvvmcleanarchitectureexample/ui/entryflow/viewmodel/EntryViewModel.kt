package com.example.androidmvvmcleanarchitectureexample.ui.entryflow.viewmodel

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.example.androidmvvmcleanarchitectureexample.ui.entryflow.models.login.UserLoginData
import com.example.androidmvvmcleanarchitectureexample.ui.entryflow.models.register.UserRegisterData
import com.example.androidmvvmcleanarchitectureexample.ui.entryflow.view.login.LoginFragment
import com.example.androidmvvmcleanarchitectureexample.ui.entryflow.view.register.RegisterSecondPartFragment
import com.example.common.utils.helper.SingleLiveEvent
import com.example.core.viewmodel.BaseViewModel
import com.example.data.features.entryflow.models.request.LoginRequest
import com.example.data.features.entryflow.models.request.register.*
import com.example.data.features.entryflow.usecases.LoginUserUseCase
import com.example.data.features.entryflow.usecases.RegisterUserUseCase
import com.example.data.helper.manager.UserDataManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EntryViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val applicationData: Application,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel(savedStateHandle, applicationData) {


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
        val userName = userLoginData.userName.get()
        val userPassword = userLoginData.userPassword.get()
        loginUserUseCase.execute(
            LoginRequest(
                username = userName,
                password = userPassword
            ),
            successOperation = {
                it.invoke()?.authToken?.let { authToken ->
                    UserDataManager.saveApiToken(authToken, applicationData.applicationContext)
                }
                userName?.let {
                    UserDataManager.saveUserName(userName, applicationData.applicationContext)
                }
                navigationRouteId.postValue(
                    LoginFragment::class.java to ZERO
                )
            }
        )
    }

    fun onSignUpBtnClicked() {

        val requestData = RegisterRequestData(
            userData = UserData(
                birthDate = null,
                firstName = userRegisterData.firstName.get(),
                lastName = userRegisterData.lastName.get(),
                middleName = userRegisterData.middleName.get(),
                password = userRegisterData.password.get(),
                userName = userRegisterData.userName.get(),
            ),
            contractData = ContractData(
                locationData = LocationData(
                    country = userRegisterData.country.get(),
                    streetName = userRegisterData.streetName.get(),
                    streetNumber = userRegisterData.streetNumber.get(),
                ),
                mail = userRegisterData.mail.get(),
                phoneNumber = userRegisterData.phoneNumber.get()
            ),
            businessData = BusinessData(
                businessAddress = userRegisterData.businessAddress.get(),
                businessMailAddress = userRegisterData.businessMailAddress.get(),
                businessName = userRegisterData.businessName.get(),
                businessRegistrationNumber = userRegisterData.businessRegistrationNumber.get(),
                businessVatNumber = userRegisterData.businessVatNumber.get(),
            )
        )

        registerUserUseCase.execute(
            RegisterRequest(
                requestData = requestData
            ),
            successOperation = {
                it.invoke()?.authToken?.let { authToken ->
                    UserDataManager.saveApiToken(authToken, applicationData.applicationContext)
                }
                userLoginData.userName.set(requestData.userData?.userName)
                navigationRouteId.postValue(
                    RegisterSecondPartFragment::class.java to ZERO
                )
            }
        )

    }

    companion object {
        const val ZERO = 0
    }

}

package com.example.data.features.entryflow.usecases

import com.example.data.base.models.DataWrapper
import com.example.data.base.models.ModelWrapper
import com.example.data.base.usecase.BaseUseCaseForNetwork
import com.example.data.features.entryflow.models.request.LoginRequest
import com.example.data.features.entryflow.models.responces.LoginResponse
import com.example.data.features.entryflow.repository.EntryFlowRepository
import retrofit2.Response
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val repository: EntryFlowRepository
) : BaseUseCaseForNetwork<LoginResponse, LoginRequest>() {

    override var isShowBaseLoadingIndicator = true

    override suspend fun run(params: LoginRequest): DataWrapper<Response<ModelWrapper<LoginResponse>>> {
        return repository.loginUser(params.asRequestWrapper)
    }

}
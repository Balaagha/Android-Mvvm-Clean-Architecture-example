package com.example.data.features.entryflow.repository

import android.content.Context
import com.example.data.base.models.DataWrapper
import com.example.data.base.models.ModelWrapper
import com.example.data.base.models.RequestWrapper
import com.example.data.base.repository.BaseRepository
import com.example.data.features.entryflow.models.request.LoginRequest
import com.example.data.features.entryflow.models.responces.LoginResponse
import com.example.data.features.entryflow.services.EntryFlowServices
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

interface EntryFlowRepository {

    suspend fun loginUser(requestData: RequestWrapper<LoginRequest>): DataWrapper<Response<ModelWrapper<LoginResponse>>>

}

@Singleton
class EntryFlowRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val services: EntryFlowServices,
) : BaseRepository(context),EntryFlowRepository {

    override suspend fun loginUser(requestData: RequestWrapper<LoginRequest>): DataWrapper<Response<ModelWrapper<LoginResponse>>> {
        return safeApiCall {
            services.login(
                queries = requestData.requestValue.getQueries(),
                headers = requestData.requestValue.getHeaders()
            )
        }
    }

}
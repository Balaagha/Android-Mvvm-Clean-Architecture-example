package com.example.data.features.common.usecase

import com.example.data.features.common.repository.DataStoreRepository
import javax.inject.Inject

class SaveBackOnlineStatus @Inject constructor(
    private val repository: DataStoreRepository
)  {

     suspend operator fun invoke (value: Boolean) {
        return repository.saveBackOnline(value)
    }

}
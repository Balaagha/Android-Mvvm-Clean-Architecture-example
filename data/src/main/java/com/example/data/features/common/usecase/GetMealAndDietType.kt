package com.example.data.features.common.usecase

import com.example.data.features.common.model.MealAndDietType
import com.example.data.features.common.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMealAndDietType @Inject constructor(
    private val repository: DataStoreRepository
)  {

    operator fun invoke (): Flow<MealAndDietType> {
        return repository.readMealAndDietType
    }

}
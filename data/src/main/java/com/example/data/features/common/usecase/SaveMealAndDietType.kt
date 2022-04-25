package com.example.data.features.common.usecase

import com.example.data.features.common.repository.DataStoreRepository
import javax.inject.Inject

class SaveMealAndDietType @Inject constructor(
    private val repository: DataStoreRepository,
) {

    suspend operator fun invoke(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int,
    ) {
        return repository.saveMealAndDietType(
            mealType = mealType,
            mealTypeId = mealTypeId,
            dietType = dietType,
            dietTypeId = dietTypeId
        )
    }

}
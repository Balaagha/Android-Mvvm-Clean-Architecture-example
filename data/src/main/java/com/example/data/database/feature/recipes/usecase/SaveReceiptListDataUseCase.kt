package com.example.data.database.feature.recipes.usecase

import com.example.data.database.feature.recipes.model.RecipesEntity
import com.example.data.database.feature.recipes.repository.ReceiptDataSource
import javax.inject.Inject

class SaveReceiptListDataUseCase @Inject constructor(
    private val repository: ReceiptDataSource
)  {

    suspend operator fun invoke (value: RecipesEntity) {
        repository.insertRecipesList(value)
    }

}
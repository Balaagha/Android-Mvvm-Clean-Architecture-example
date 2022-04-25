package com.example.data.database.feature.recipes.usecase

import com.example.data.database.feature.recipes.model.RecipesEntity
import com.example.data.database.feature.recipes.repository.ReceiptDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReceiptListDataUseCase @Inject constructor(
    private val repository: ReceiptDataSource
)  {

    operator fun invoke (): Flow<List<RecipesEntity>> {
        return repository.getReceiptListData()
    }

}
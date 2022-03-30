package com.example.data.features.recipes.usecase

import com.example.data.base.models.DataWrapper
import com.example.data.base.usecase.BaseUseCaseForNetwork
import com.example.data.features.recipes.models.FoodRecipe
import com.example.data.features.recipes.repository.RecipesRepository
import retrofit2.Response
import javax.inject.Inject

class GetRecipesUseCaseForNetwork @Inject constructor(
    private val recipesRepository: RecipesRepository
) : BaseUseCaseForNetwork<FoodRecipe, Map<String, String>>() {

    override suspend fun run(params: Map<String, String>): DataWrapper<Response<FoodRecipe>> {
        return recipesRepository.getRecipes(params.asRequestWrapper)
    }

}
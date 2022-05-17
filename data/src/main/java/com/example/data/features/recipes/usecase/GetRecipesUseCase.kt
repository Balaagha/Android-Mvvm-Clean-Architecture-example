//package com.example.data.features.recipes.usecase
//
//import com.example.data.base.models.DataWrapper
//import com.example.data.base.models.FailureType
//import com.example.data.base.models.ModelWrapper
//import com.example.data.base.usecase.BaseUseCaseForNetwork
//import com.example.data.features.recipes.models.FoodRecipe
//import com.example.data.features.recipes.repository.RecipesRepository
//import retrofit2.Response
//import javax.inject.Inject
//
//class GetRecipesUseCase @Inject constructor(
//    private val recipesRepository: RecipesRepository
//) : BaseUseCaseForNetwork<FoodRecipe, Map<String, String>>() {
//
//    override var isShowBaseLoadingIndicator = true
//
//    override suspend fun run(params: Map<String, String>): DataWrapper<Response<ModelWrapper<FoodRecipe>>> {
//        return recipesRepository.getRecipes(params.asRequestWrapper)
//    }
//
//    override fun parseResult(data: DataWrapper<Response<FoodRecipe>>): DataWrapper<FoodRecipe> {
//        if(data is DataWrapper.Success){
//            when{
//                data.value.code() == 402 ->{
//                    return DataWrapper.Failure(
//                        failureType = FailureType.OTHER,
//                        message = "API Key Limited"
//                    )
//                }
//                data.value.body()!!.recipesResults.isNullOrEmpty()->{
//                    return  DataWrapper.Failure(
//                        failureType = FailureType.OTHER,
//                        message = "Recipes not found"
//                    )
//                }
//                else -> return super.parseResult(data)
//            }
//        } else {
//            return super.parseResult(data)
//        }
//    }
//
//}
package com.example.androidmvvmcleanarchitectureexample.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.core.viewmodel.BaseViewModel
import com.example.data.base.models.DataWrapper
import com.example.data.database.feature.recipes.model.RecipesEntity
import com.example.data.database.feature.recipes.usecase.GetReceiptListDataUseCase
import com.example.data.database.feature.recipes.usecase.SaveReceiptListDataUseCase
import com.example.data.features.recipes.models.FoodRecipe
import com.example.data.features.recipes.usecase.GetRecipesUseCase
import com.example.data.features.recipes.usecase.SearchRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getRecipesUseCase: GetRecipesUseCase,
    private val searchRecipesUseCase: SearchRecipesUseCase,
    getReceiptListDataUseCase: GetReceiptListDataUseCase,
    private val saveReceiptListDataUseCase: SaveReceiptListDataUseCase,
    application: Application,
    savedStateHandle: SavedStateHandle
) : BaseViewModel(savedStateHandle, application) {

    /** ROOM DATABASE */
    val readRecipes: LiveData<List<RecipesEntity>> = getReceiptListDataUseCase.invoke().asLiveData()

    /** RETROFIT */
    var recipesResponse: MutableLiveData<DataWrapper<FoodRecipe>> = MutableLiveData()
    var searchedRecipesResponse: MutableLiveData<DataWrapper<FoodRecipe>> = MutableLiveData()

    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesUseCase.execute(
            params = queries,
            successOperation = {

            },
            failOperation = {

            }
        ){ 
            recipesResponse.value = it
            it.let {

            }
        }
        recipesResponse.value = DataWrapper.Loading()
        if (hasInternetConnection()) {

            try {
                recipesResponse.value = getRecipesUseCase.invoke(queries)
                recipesResponse.value?.invoke()?.let {
                    offlineCacheRecipes(it)
                }
            } catch (e: Exception) {
                recipesResponse.value = DataWrapper.Failure(message = "Recipes not found.")
            }
        } else {
            recipesResponse.value = DataWrapper.Failure(message = "No Internet Connection")
        }
    }


    fun searchRecipes(searchQuery: Map<String, String>) = viewModelScope.launch {
        searchedRecipesResponse.value = DataWrapper.Loading()
        if (hasInternetConnection()) {
            try {
                searchedRecipesResponse.value = searchRecipesUseCase.invoke(searchQuery)
            } catch (e: Exception) {
                searchedRecipesResponse.value = DataWrapper.Failure(message = "Recipes not found.")
            }
        } else {
            searchedRecipesResponse.value = DataWrapper.Failure(message = "No Internet Connection.")
        }
    }


    private fun offlineCacheRecipes(foodRecipe: FoodRecipe) {
        viewModelScope.launch(Dispatchers.IO) {
            saveReceiptListDataUseCase.invoke(
                RecipesEntity(foodRecipe)
            )
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}
package com.example.androidmvvmcleanarchitectureexample.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.data.base.models.DataWrapper
import com.example.data.database.feature.recipes.model.RecipesEntity
import com.example.data.features.recipes.models.FoodRecipe

class RecipesBinding {

    companion object {

        @BindingAdapter("readApiResponse", "readDatabase", requireAll = true)
        @JvmStatic
        fun errorImageViewVisibility(
            imageView: ImageView,
            apiResponse: DataWrapper<FoodRecipe>?,
            database: List<RecipesEntity>?
        ) {
            if (apiResponse is DataWrapper.Failure && database.isNullOrEmpty()) {
                imageView.visibility = View.VISIBLE
            } else if (apiResponse is DataWrapper.Loading) {
                imageView.visibility = View.INVISIBLE
            } else if (apiResponse is DataWrapper.Success) {
                imageView.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("readApiResponse2", "readDatabase2", requireAll = true)
        @JvmStatic
        fun errorTextViewVisibility(
            textView: TextView,
            apiResponse: DataWrapper<FoodRecipe>?,
            database: List<RecipesEntity>?
        ) {
            if (apiResponse is DataWrapper.Failure && database.isNullOrEmpty()) {
                textView.visibility = View.VISIBLE
                textView.text = apiResponse.message.toString()
            } else if (apiResponse is DataWrapper.Loading) {
                textView.visibility = View.INVISIBLE
            } else if (apiResponse is DataWrapper.Success) {
                textView.visibility = View.INVISIBLE
            }
        }

    }

}
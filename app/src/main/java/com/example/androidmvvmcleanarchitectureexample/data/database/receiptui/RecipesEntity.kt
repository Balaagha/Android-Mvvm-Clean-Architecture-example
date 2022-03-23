package com.example.androidmvvmcleanarchitectureexample.data.database.receiptui

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidmvvmcleanarchitectureexample.data.database.common.DatabaseConstant.RECIPES_TABLE
import com.example.data.features.recipes.models.FoodRecipe

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}
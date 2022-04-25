package com.example.data.database.feature.recipes.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.database.utils.DatabaseConstant.RECIPES_TABLE
import com.example.data.features.recipes.models.FoodRecipe

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}
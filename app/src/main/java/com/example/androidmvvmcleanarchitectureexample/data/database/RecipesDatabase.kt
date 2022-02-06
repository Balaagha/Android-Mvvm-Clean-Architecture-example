package com.example.androidmvvmcleanarchitectureexample.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.androidmvvmcleanarchitectureexample.data.database.RecipesDao
import com.example.androidmvvmcleanarchitectureexample.data.database.RecipesEntity
import com.example.androidmvvmcleanarchitectureexample.data.database.RecipesTypeConverter

@Database(
    entities = [RecipesEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RecipesTypeConverter::class)
abstract class RecipesDatabase: RoomDatabase() {

    abstract fun recipesDao(): RecipesDao

}
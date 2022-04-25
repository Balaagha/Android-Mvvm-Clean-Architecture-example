package com.example.data.database.base

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.database.feature.mobilenumber.dao.ContactListDao
import com.example.data.database.feature.mobilenumber.model.CachedMobileNumber
import com.example.data.database.feature.recipes.dao.RecipesDao
import com.example.data.database.feature.recipes.model.RecipesEntity

@Database(
    entities = [
        RecipesEntity::class,
        CachedMobileNumber::class
    ],
    version = 6,
    exportSchema = false
)
@TypeConverters(TypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun recipesDao(): RecipesDao
    abstract fun contactListDao(): ContactListDao

}
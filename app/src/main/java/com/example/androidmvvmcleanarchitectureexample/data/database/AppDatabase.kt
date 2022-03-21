package com.example.androidmvvmcleanarchitectureexample.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.androidmvvmcleanarchitectureexample.data.database.contactlist.CachedMobileNumber
import com.example.androidmvvmcleanarchitectureexample.data.database.contactlist.ContactListDao
import com.example.androidmvvmcleanarchitectureexample.data.database.receiptui.RecipesDao
import com.example.androidmvvmcleanarchitectureexample.data.database.receiptui.RecipesEntity

@Database(
    entities = [
        RecipesEntity::class,
        CachedMobileNumber::class
    ],
    version = 6,
    exportSchema = false
)
@TypeConverters(RecipesTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun recipesDao(): RecipesDao
    abstract fun contactListDao(): ContactListDao

}
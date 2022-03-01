package com.example.androidmvvmcleanarchitectureexample.data.database.contactlist

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.androidmvvmcleanarchitectureexample.data.database.common.DatabaseConstant

@Entity(
    tableName = DatabaseConstant.MOBILE_TABLE,
    indices = [Index(value = ["number"], unique = false)]
)
data class CachedMobileNumber(
    @PrimaryKey
    val number: String,
)
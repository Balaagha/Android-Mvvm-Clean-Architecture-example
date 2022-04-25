package com.example.data.database.feature.mobilenumber.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.data.database.utils.DatabaseConstant

@Entity(
    tableName = DatabaseConstant.MOBILE_TABLE,
    indices = [Index(value = ["number"], unique = false)]
)
data class CachedMobileNumber(
    @PrimaryKey
    val number: String,
)
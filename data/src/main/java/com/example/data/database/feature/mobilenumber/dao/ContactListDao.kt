package com.example.data.database.feature.mobilenumber.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.data.database.base.BaseDao
import com.example.data.database.feature.mobilenumber.model.CachedMobileNumber
import com.example.data.database.utils.DatabaseConstant.MOBILE_TABLE

@Dao
interface ContactListDao: BaseDao<CachedMobileNumber> {
    @Query("SELECT * FROM $MOBILE_TABLE")
    suspend fun getContactList(): List<CachedMobileNumber>
    @Query("DELETE FROM $MOBILE_TABLE")
    suspend fun clearAll()
}
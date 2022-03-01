package com.example.androidmvvmcleanarchitectureexample.data.database.contactlist

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.androidmvvmcleanarchitectureexample.data.database.common.BaseDao
import com.example.androidmvvmcleanarchitectureexample.data.database.common.DatabaseConstant.MOBILE_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactListDao: BaseDao<CachedMobileNumber> {
    @Query("SELECT * FROM $MOBILE_TABLE")
    suspend fun getContactList(): List<CachedMobileNumber>
    @Query("DELETE FROM $MOBILE_TABLE")
    suspend fun clearAll()
}
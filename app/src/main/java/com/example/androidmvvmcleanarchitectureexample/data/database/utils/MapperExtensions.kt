package com.example.androidmvvmcleanarchitectureexample.data.database.utils

import com.example.androidmvvmcleanarchitectureexample.data.database.contactlist.CachedMobileNumber
import com.example.androidmvvmcleanarchitectureexample.data.database.contactlist.MobileNumber

// Contact list region
fun MobileNumber.toCache() = CachedMobileNumber(
    number = number
)

fun CachedMobileNumber.toDomain() = MobileNumber(
    number = number
)
// End region
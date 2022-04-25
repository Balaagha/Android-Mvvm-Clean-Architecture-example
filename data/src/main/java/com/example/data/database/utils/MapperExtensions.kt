package com.example.data.database.utils

import com.example.data.database.feature.mobilenumber.model.CachedMobileNumber
import com.example.data.database.feature.mobilenumber.model.MobileNumber

// Contact list region
fun MobileNumber.toCache() = CachedMobileNumber(
    number = number
)

fun CachedMobileNumber.toDomain() = MobileNumber(
    number = number
)
// End region
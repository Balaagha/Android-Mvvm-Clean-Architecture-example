package com.example.data.helper.models.typedefs

import androidx.annotation.StringDef

object SharedTypes {
    const val USER_DATA = "USER_DATA"

    @Retention(AnnotationRetention.SOURCE)
    @StringDef(
        USER_DATA
    )
    annotation class SharedTypesDef
}
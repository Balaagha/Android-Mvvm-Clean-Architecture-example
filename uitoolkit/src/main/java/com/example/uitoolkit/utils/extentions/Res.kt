package com.example.uitoolkit.utils.extentions

import android.content.Context
import androidx.core.content.ContextCompat

fun Int.asColorInt(context: Context) = ContextCompat.getColor(context, this)
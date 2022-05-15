package com.example.androidmvvmcleanarchitectureexample.ui.entryflow.utils

import com.google.android.material.textfield.TextInputLayout

fun checkInputValidation(inputLayout: TextInputLayout, minLength: Int): Boolean {
    inputLayout.editText?.text?.let {
        val isValid = it.length > minLength
        inputLayout.error = if (isValid) null else "wrong input"
        return it.length > minLength
    } ?: run {
        return false
    }
}
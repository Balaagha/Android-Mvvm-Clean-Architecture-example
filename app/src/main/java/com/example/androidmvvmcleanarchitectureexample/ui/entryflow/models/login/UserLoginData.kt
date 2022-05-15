package com.example.androidmvvmcleanarchitectureexample.ui.entryflow.models.login

import androidx.databinding.ObservableField

class UserLoginData {
    val userName: ObservableField<String> = ObservableField()
    val userPassword: ObservableField<String> = ObservableField()
}
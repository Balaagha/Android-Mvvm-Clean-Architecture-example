package com.example.androidmvvmcleanarchitectureexample.ui.entryflow.models.register

import androidx.databinding.ObservableField

class UserRegisterData {
    val firstName: ObservableField<String> = ObservableField()
    val lastName: ObservableField<String> = ObservableField()
    val middleName: ObservableField<String> = ObservableField()
    val birthDate: ObservableField<String> = ObservableField()
    val userName: ObservableField<String> = ObservableField()
    val password: ObservableField<String> = ObservableField()
    val rePassword: ObservableField<String> = ObservableField()

    val mail: ObservableField<String> = ObservableField()
    val phoneNumber: ObservableField<String> = ObservableField()

    val country: ObservableField<String> = ObservableField()
    val streetName: ObservableField<String> = ObservableField()
    val streetNumber: ObservableField<String> = ObservableField()


    val businessAddress: ObservableField<String> = ObservableField()
    val businessMailAddress: ObservableField<String> = ObservableField()
    val businessName: ObservableField<String> = ObservableField()
    val businessRegistrationNumber: ObservableField<String> = ObservableField()
    val businessVatNumber: ObservableField<String> = ObservableField()
}
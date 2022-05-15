package com.example.androidmvvmcleanarchitectureexample.ui.entryflow.viewmodel

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.example.core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EntryViewModel @Inject constructor(
    application: Application,
    savedStateHandle: SavedStateHandle
) : BaseViewModel(savedStateHandle, application) {

    var data = "UnInitial"


    
}
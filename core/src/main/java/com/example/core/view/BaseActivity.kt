package com.example.core.view

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity() {

    abstract fun showLoadingDialog(): Boolean

    abstract fun hideLoadingDialog(): Boolean

}
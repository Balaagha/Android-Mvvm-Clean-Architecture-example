package com.example.data.helper.manager

import android.content.Context
import com.example.data.helper.models.typedefs.SharedTypes
import com.example.data.utils.SharedConstant


object UserDataManager {

    private var userName: String? = null
    private var apiToken: String? = null

    fun saveUserName(value: String, context: Context){
        this.userName = value
        SharedPrefsManager(context,SharedTypes.USER_DATA).set(SharedConstant.USER_NAME, value)
    }

    fun saveApiToken(value: String, context: Context){
        this.apiToken = value
        SharedPrefsManager(context,SharedTypes.USER_DATA).set(SharedConstant.API_TOKEN, value)
    }

    fun getUserName(context: Context): String?{
        if(this.userName.isNullOrEmpty()){
            userName = SharedPrefsManager(context,SharedTypes.USER_DATA).get(SharedConstant.USER_NAME, null)
        }

        return this.userName
    }

    fun getApiToken(context: Context): String? {
        if(this.apiToken.isNullOrEmpty()){
            userName = SharedPrefsManager(context,SharedTypes.USER_DATA).get(SharedConstant.API_TOKEN, null)
        }
        return this.userName
    }


}
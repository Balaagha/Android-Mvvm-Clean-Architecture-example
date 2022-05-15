package com.example.data.features.entryflow.models.request

class LoginRequest(
    var username: String?,
    var password: String?,
    var lang: String = "AZ"
) {
    fun getHeaders(): HashMap<String, Any> {
        return hashMapOf<String, Any>().apply {
            username?.let {
                put(USER_NAME, it)
            }
            password?.let {
                put(PASSWORD, it)
            }
        }
    }

    fun getQueries(): HashMap<String, Any> {
        return hashMapOf<String, Any>().apply {
            put(LANG, lang)
        }
    }

    companion object {
        const val USER_NAME = "username"
        const val PASSWORD = "password"
        const val LANG = "lang"
    }
}
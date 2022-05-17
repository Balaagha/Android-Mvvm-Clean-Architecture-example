package com.example.data.features.entryflow.models.request

class LoginRequest(
    var username: String?,
    var password: String?,
    var lang: String = "AZ"
) {
    fun getHeaders(): HashMap<String, String> {
        return hashMapOf<String, String>().apply {
            username?.let {
                put(USER_NAME, it)
            }
            password?.let {
                put(PASSWORD, it)
            }
        }
    }

    fun getQueries(): HashMap<String, String> {
        return hashMapOf<String, String>().apply {
            put(LANG, lang)
        }
    }

    companion object {
        const val USER_NAME = "username"
        const val PASSWORD = "password"
        const val LANG = "lang"
    }
}
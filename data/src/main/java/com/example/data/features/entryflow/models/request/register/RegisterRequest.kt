package com.example.data.features.entryflow.models.request.register

class RegisterRequest(
    requestData: RegisterRequestData,
    var lang: String = "AZ"
) {

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
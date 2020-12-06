package com.example.movementapp.models
import com.google.gson.Gson

class Token {
    var access_token: String? = null
    val refresh_token: String? = null
    var expires_in: String? = null

    internal fun dump(token: Token): String{
        return Gson().toJson(token).toString()
    }

}
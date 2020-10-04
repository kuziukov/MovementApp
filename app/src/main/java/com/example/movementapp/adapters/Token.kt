package com.example.movementapp.adapters
import com.google.gson.Gson

class Token {
    var access_token: String? = null
    var expires_in: String? = null

    internal fun dump(token: Token): String{
        return Gson().toJson(token).toString()
    }

    internal fun load(string: String): Token{
        return Gson().fromJson(string, Token::class.java)
    }

    fun isExpired(timestamp: Long): Boolean {
        return expires_in.toString().toDouble() < timestamp
    }
}
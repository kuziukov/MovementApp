package com.example.movementapp.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.example.movementapp.models.Token
import com.google.gson.Gson

val privateStore = "privateStore"


fun load_token(context: Context) {
    val store = context.getSharedPreferences(privateStore, Context.MODE_PRIVATE)
    //return Token().load(store.getString("token", "{}").toString())
}

fun save_token(activity: Activity, response: String): Token {
    var store: SharedPreferences.Editor = activity.getSharedPreferences(privateStore,
        Context.MODE_PRIVATE
    ).edit()
    val token: Token = Gson().fromJson(response, Token::class.java)
    store.putString("token", Token().dump(token))
    store.commit()
    return token
}

fun clear_token(activity: Activity){
    var store: SharedPreferences.Editor = activity.getSharedPreferences(privateStore,
        Context.MODE_PRIVATE
    ).edit()
    store.putString("token", {}.toString())
    store.commit()
}
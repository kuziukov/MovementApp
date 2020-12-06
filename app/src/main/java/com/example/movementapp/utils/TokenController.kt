package com.example.movementapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.movementapp.models.Token
import java.lang.IllegalStateException

class TokenController(context: Context) {

    private val STORE = "ftravelerStore"
    private val ACCESS_TOKEN_VALUE = "com.example.app.ACCESS_TOKEN_VALUE"
    private val REFRESH_TOKEN_VALUE = "com.example.app.REFRESH_TOKEN_VALUE"

    var token: Token? = null
    var context: Context? = null

    private var sInstance: TokenController? = null
    private var mPref: SharedPreferences? = null

    init {
        mPref = context.getSharedPreferences(STORE, Context.MODE_PRIVATE)
    }

    @Synchronized
    fun initializeInstance(context: Context) {
        if (sInstance == null) {
            sInstance = TokenController(context)
        }
    }

    @Synchronized
    fun getInstance(): TokenController? {
        if (sInstance == null) {
            throw IllegalStateException(
                TokenController::class.java.getSimpleName() +
                        " is not initialized, call initializeInstance(..) method first."
            )
        }
        return sInstance
    }

    fun setAccessToken(value: String){
        mPref!!.edit()
            .putString(ACCESS_TOKEN_VALUE, value)
            .apply();
    }

    fun getAccessToken(): String {
        return mPref!!.getString(ACCESS_TOKEN_VALUE, "").toString()
    }

    fun setRefreshToken(value: String){
        mPref!!.edit()
            .putString(REFRESH_TOKEN_VALUE, value)
            .apply()
    }

    fun getRefreshToken(): String {
        return mPref!!.getString(REFRESH_TOKEN_VALUE, "").toString()
    }

    fun remove(key: String){
        mPref!!.edit()
            .remove(key)
            .apply()
    }

    fun clear(): Boolean {
        return mPref!!.edit()
            .clear()
            .commit()
    }

}
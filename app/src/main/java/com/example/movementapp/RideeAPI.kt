package com.example.movementapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import com.example.movementapp.adapters.CodeVerification
import com.example.movementapp.adapters.PhoneVerification
import com.example.movementapp.adapters.ResponseAPI
import com.example.movementapp.adapters.User

interface RideeAPI {
    interface APICallBack {
        fun onResponse(
            responseAPI: ResponseAPI?
        )
        fun onFailure(cause: String?)
    }
    @POST("/v1.0/authorization/sms")
    fun phoneVerification(@Body number: PhoneVerification?): Call<ResponseAPI?>?

    @POST("/v1.0/authorization/sms/complete")
    fun codeVerification(@Body codeVerification: CodeVerification?): Call<ResponseAPI?>?

    @GET("/v1.0/user")
    fun getUser(@Header("Token") access_token: String): Call<ResponseAPI?>?

    @POST("/v1.0/user")
    fun changeUser(@Body user: User?, @Header("Token") access_token: String): Call<ResponseAPI?>?
}
package com.example.movementapp.controller

import com.example.movementapp.RideeAPI
import com.example.movementapp.adapters.CodeVerification
import com.example.movementapp.adapters.PhoneVerification
import com.example.movementapp.adapters.ResponseAPI
import com.example.movementapp.adapters.User
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserPostController(APICallBack: RideeAPI.APICallBack?) {

    var APICallBack: RideeAPI.APICallBack? = null
    init {
        this.APICallBack = APICallBack
    }

    fun start(user: User, access_token: String) {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val rideeAPI: RideeAPI = retrofit.create(RideeAPI::class.java)
        val call: Call<ResponseAPI?>? = rideeAPI.changeUser(user, access_token)
        call!!.enqueue(object : Callback<ResponseAPI?> {
            override fun onResponse(
                call: Call<ResponseAPI?>?,
                res: Response<ResponseAPI?>
            ) {
                val response: ResponseAPI? = res.body()
                if(response?.code == 200){
                    APICallBack?.onResponse(response)
                }
                APICallBack?.onFailure(response?.result.toString())
            }

            override fun onFailure(
                call: Call<ResponseAPI?>?,
                t: Throwable
            ) {
                APICallBack?.onFailure(t.printStackTrace().toString());
            }
        })
    }
    companion object {
        const val BASE_URL = "https://api.wlusm.ru/"
    }
}
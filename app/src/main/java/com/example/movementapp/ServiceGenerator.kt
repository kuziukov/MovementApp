package com.example.movementapp

import android.content.Context
import android.text.TextUtils
import com.example.movementapp.adapters.ErrorHandlingCallAdapterFactory
import com.example.movementapp.controller.TokenAuthenticator
import com.example.movementapp.interceptors.AuthenticationInterceptor
import com.example.movementapp.utils.TokenController
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ServiceGenerator {
    private const val BASE_URL = "https://api.ftraveler.com/"
    private val builder = Retrofit.Builder()
                                    .baseUrl(BASE_URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .addCallAdapterFactory(ErrorHandlingCallAdapterFactory())
    private var retrofit = builder.build()
    private val logging: HttpLoggingInterceptor = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
    private val httpClient = OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)


    fun <S> createService(serviceClass: Class<S>, context: Context): S {
        val authToken: String = TokenController(context).getAccessToken()
        if (!TextUtils.isEmpty(authToken)) {
            val interceptor =
                AuthenticationInterceptor(
                    authToken
                )
            val authenticator =
                TokenAuthenticator(context)

            httpClient.authenticator(authenticator)
            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor)
            }
            if(!httpClient.interceptors().contains(logging)){
                httpClient.addInterceptor(logging)
            }
            builder.client(
                httpClient.build())
            retrofit = builder.build()
        }
        return retrofit.create(serviceClass)
    }
}
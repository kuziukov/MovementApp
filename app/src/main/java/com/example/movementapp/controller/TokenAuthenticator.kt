package com.example.movementapp.controller

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.movementapp.ServiceGenerator
import com.example.movementapp.SplashActivity
import com.example.movementapp.interfaces.FTravelerAPI
import com.example.movementapp.interfaces.MyCall
import com.example.movementapp.models.RefreshToken
import com.example.movementapp.models.ResponseAPI
import com.example.movementapp.models.Token
import com.example.movementapp.utils.TokenController
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route


class TokenAuthenticator(private val context: Context) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val tokenController = TokenController(context)
        val refreshToken = RefreshToken()
        refreshToken.refresh_token = tokenController.getRefreshToken()
        val api: FTravelerAPI = ServiceGenerator.createService(
            FTravelerAPI::class.java,
            context
        )
        val call: MyCall<ResponseAPI> = api.refreshToken(refreshToken)
        try {
            val res: retrofit2.Response<ResponseAPI> = call.execute()
            if (res.isSuccessful) {
                val responseAPI: ResponseAPI = res.body()
                println(responseAPI.result)
                val token: Token = Gson().fromJson(responseAPI.result.toString(), Token::class.java)
                tokenController.setAccessToken(token.access_token.toString())
                tokenController.setRefreshToken(token.refresh_token.toString())
                return response.request.newBuilder()
                    .header("Token", tokenController.getAccessToken())
                    .build();
            } else {
                println(res.code())
                if(res.code() == 402){
                    tokenController.clear()
                    context.startActivity(Intent(context as Activity, SplashActivity::class.java))
                    context.finish()
                }
            }
        } catch (ex: Exception) {
            println(ex.message.toString())
        }
        return null
    }

}

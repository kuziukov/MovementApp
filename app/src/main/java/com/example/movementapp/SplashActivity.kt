package com.example.movementapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.movementapp.utils.TokenController
import io.jsonwebtoken.Jwts
import java.util.*


class SplashActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tokenController: TokenController = TokenController(applicationContext)
        var access_token: String = tokenController.getAccessToken()

        var intent: Intent? = null
        if (access_token != "") {
            try {
                val i: Int = access_token.lastIndexOf('.')
                access_token = access_token.substring(0, i+1);
                val untrusted = Jwts.parser().parseClaimsJwt(access_token);
                if (untrusted.body.expiration.after(Date())){
                    intent = Intent(this, MainActivity::class.java)
                }
            } catch (e: Exception) {
                intent = Intent(this, LoginActivity::class.java)
                tokenController.clear()
            }
        } else {
            intent = Intent(this, LoginActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}

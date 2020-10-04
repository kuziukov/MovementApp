package com.example.movementapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.movementapp.adapters.Token
import com.example.movementapp.adapters.User
import com.google.gson.Gson
import java.time.Instant


class SplashActivity : AppCompatActivity() {

    val privateStore = "privateStore"
    private lateinit var store: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        store = getSharedPreferences(privateStore, Context.MODE_PRIVATE)

        val token: Token = Token().load(store.getString("token", "{}").toString())
        val user: User = Gson().fromJson(store.getString("user", "{}").toString(), User::class.java)
        val timestampNow: Long = Instant.now().epochSecond
        val intent: Intent

        if (token.access_token == null || user.name == null || user.surname == null || token.isExpired(timestampNow)){
            intent = Intent(this, LoginActivity::class.java)
        }else{
            intent = Intent(this, MainActivity::class.java)
        }
        startActivity(intent)
        finish()


    }
}

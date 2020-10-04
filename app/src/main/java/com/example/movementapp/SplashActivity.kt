package com.example.movementapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.movementapp.adapters.User
import com.google.gson.Gson


class SplashActivity : AppCompatActivity() {

    val privateStore = "privateStore"
    private lateinit var store: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        store = getSharedPreferences(privateStore, Context.MODE_PRIVATE)
        val access_token = store.getString("access_token", "").toString()
        val user: User = Gson().fromJson(store.getString("user", "{}").toString(), User::class.java)

        //TODO проверить expired_in по текущему Timestamp

        val intent: Intent
        if (access_token == "" || user.name == null || user.surname == null){
            intent = Intent(this, LoginActivity::class.java)
        }else{
            intent = Intent(this, MainActivity::class.java)
        }
        startActivity(intent)
        finish()


    }
}

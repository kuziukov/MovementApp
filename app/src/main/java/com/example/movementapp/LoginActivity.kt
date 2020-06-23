package com.example.movementapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


class LoginActivity : AppCompatActivity() {

    private lateinit var toolbarTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbarTitle = toolbar.findViewById<TextView>(R.id.toolbar_title)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false);

    }

    public fun changeToolBarTitle(id: Int){
        toolbarTitle.text = resources.getString(id)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }
}

package com.example.movementapp.utils

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.movementapp.R

class ToastCustom constructor(val inflater: LayoutInflater, var root: View){

    fun makeText(context: Context, message: String): Toast {
        val layout: View = inflater.inflate(
            R.layout.toast,
            root.findViewById(R.id.toast_layout_root) as ViewGroup?
        )
        val text = layout.findViewById<TextView>(R.id.text)
        text.text = message

        val toast = Toast(context)
        toast.setGravity(Gravity.FILL_HORIZONTAL or Gravity.BOTTOM, 0, 40)
        toast.duration = Toast.LENGTH_LONG
        toast.view = layout

        return toast
    }
}
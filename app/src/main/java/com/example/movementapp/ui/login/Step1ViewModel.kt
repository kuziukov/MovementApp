package com.example.movementapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class Step1ViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Step 1 Fragment"
    }
    val text: LiveData<String> = _text
}
package com.example.movementapp.ui.trip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BottomTravelsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Step 1 Fragment"
    }
    val text: LiveData<String> = _text
}
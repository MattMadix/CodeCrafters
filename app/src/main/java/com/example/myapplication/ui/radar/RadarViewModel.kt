package com.example.myapplication.ui.radar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RadarViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is radar Fragment"
    }
    val text: LiveData<String> = _text
}
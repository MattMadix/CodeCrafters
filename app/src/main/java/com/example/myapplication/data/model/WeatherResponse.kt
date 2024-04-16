package com.example.myapplication.data.model

import com.example.myapplication.model.CurrentWeatherResponse

sealed class WeatherResponse {
    data class Success(val weather : CurrentWeatherResponse) : WeatherResponse()
    data object Error : WeatherResponse()
}
package com.example.myapplication.data.repository

import com.example.myapplication.data.model.WeatherResponse

interface WeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double) : WeatherResponse
}
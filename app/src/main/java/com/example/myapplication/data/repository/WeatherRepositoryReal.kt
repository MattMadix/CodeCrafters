package com.example.myapplication.data.repository

import com.example.myapplication.data.WeatherApi;
import com.example.myapplication.data.model.WeatherResponse
import javax.inject.Inject;

class WeatherRepositoryReal @Inject constructor(
    private val weatherApi:WeatherApi
) : WeatherRepository {
    override suspend fun getWeather(lat: Double, lon: Double): WeatherResponse {
        val result = weatherApi.getWeather(lat, lon)
        return if (result.isSuccessful) {
            WeatherResponse.Success((result.body() ?: null)!!)
        }
        else {
            WeatherResponse.Error
        }
    }

}
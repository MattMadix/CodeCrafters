package com.example.myapplication.data

import com.example.myapplication.model.CurrentWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("/data/2.5/weather")
    suspend fun getWeather(
        @Query("lat") lat : Double,
        @Query("lon") lon : Double,
        @Query("appid") apiKey : String = "8757e637788bf0791599c94da5edc9f1",
        @Query("units") units : String = "metric"): Response<CurrentWeatherResponse>
}
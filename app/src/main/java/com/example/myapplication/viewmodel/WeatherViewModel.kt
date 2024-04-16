package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.WeatherResponse
import com.example.myapplication.data.repository.WeatherRepository
import com.example.myapplication.model.CurrentWeatherResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _weather = MutableStateFlow<WeatherEvent>(WeatherEvent.Loading)
    val weather: StateFlow<WeatherEvent> =_weather

    fun getWeather(lat: Double, lon: Double) = viewModelScope.launch {
        when (val response = weatherRepository.getWeather(lat, lon)) {
            WeatherResponse.Error -> _weather.value= WeatherEvent.Failure
            is WeatherResponse.Success -> _weather.value = WeatherEvent.Success(response.weather)
        }
    }

    sealed class WeatherEvent {
        data class Success(val weather: CurrentWeatherResponse) : WeatherEvent()
        data object Failure : WeatherEvent()
        data object Loading : WeatherEvent()
    }
}
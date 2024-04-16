package com.example.myapplication.di

import com.example.myapplication.data.repository.WeatherRepository
import com.example.myapplication.data.repository.WeatherRepositoryReal
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepositoryReal: WeatherRepositoryReal
    ): WeatherRepository
}
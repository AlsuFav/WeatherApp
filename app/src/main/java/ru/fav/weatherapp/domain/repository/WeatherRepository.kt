package ru.fav.weatherapp.domain.repository

import ru.fav.weatherapp.domain.model.WeatherModel

interface WeatherRepository {
    suspend fun getWeatherByCityName(city : String, units: String): WeatherModel
}
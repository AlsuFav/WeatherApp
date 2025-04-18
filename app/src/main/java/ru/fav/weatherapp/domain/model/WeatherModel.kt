package ru.fav.weatherapp.domain.model

data class WeatherModel(
    val currentTemp: Float = 0F,
    val windSpeed: Float = 0F,
    val humidity: Int = 0,
    val sunrise: Long = 0,
    val sunset: Long = 0,
    val description: String = "",
    val icon: String = ""
)
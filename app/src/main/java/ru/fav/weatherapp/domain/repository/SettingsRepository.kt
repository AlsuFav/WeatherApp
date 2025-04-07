package ru.fav.weatherapp.domain.repository

interface SettingsRepository {
    fun getTemperatureUnit(): String
}
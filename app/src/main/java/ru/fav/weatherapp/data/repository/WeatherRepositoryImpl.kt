package ru.fav.weatherapp.data.repository

import ru.fav.weatherapp.data.mapper.WeatherResponseMapper
import ru.fav.weatherapp.data.remote.OpenWeatherApi
import ru.fav.weatherapp.domain.model.WeatherModel
import ru.fav.weatherapp.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val weatherApi: OpenWeatherApi,
    private val mapper: WeatherResponseMapper,
): WeatherRepository {
    override suspend fun getWeatherByCityName(city: String): WeatherModel {
        return weatherApi.getCurrentWeatherByCityName(city = city).let(mapper::map)
    }
}
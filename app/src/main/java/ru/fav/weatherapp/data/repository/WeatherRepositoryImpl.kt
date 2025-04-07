package ru.fav.weatherapp.data.repository

import ru.fav.weatherapp.data.mapper.WeatherResponseMapper
import ru.fav.weatherapp.data.remote.OpenWeatherApi
import ru.fav.weatherapp.domain.model.WeatherModel
import ru.fav.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: OpenWeatherApi,
    private val mapper: WeatherResponseMapper,
): WeatherRepository {
    override suspend fun getWeatherByCityName(city: String, units: String): WeatherModel {
        return weatherApi.getCurrentWeatherByCityName(city = city, units = units).let(mapper::map)
    }
}
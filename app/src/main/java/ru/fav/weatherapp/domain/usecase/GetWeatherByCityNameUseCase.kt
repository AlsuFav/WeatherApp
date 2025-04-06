package ru.fav.weatherapp.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.fav.weatherapp.domain.model.WeatherModel
import ru.fav.weatherapp.domain.repository.WeatherRepository

class GetWeatherByCityNameUseCase(
    private val weatherRepository: WeatherRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(city: String): WeatherModel {
        return withContext(ioDispatcher) {
            val weatherData =  weatherRepository.getWeatherByCityName(city = city)
            if (weatherData.currentTemp == 0F) {
                throw IllegalArgumentException("Failure")
            } else {
                weatherData
            }
        }
    }
}
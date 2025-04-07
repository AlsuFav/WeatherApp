package ru.fav.weatherapp.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.fav.weatherapp.domain.exception.WrongTempException
import ru.fav.weatherapp.domain.model.WeatherModel
import ru.fav.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherByCityNameUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(city: String, units: String): WeatherModel {
        return withContext(ioDispatcher) {
            val weatherData =  weatherRepository.getWeatherByCityName(city = city, units = units)
            if (weatherData.currentTemp == 0F) {
                throw WrongTempException(cause = null)
            } else {
                weatherData
            }
        }
    }
}
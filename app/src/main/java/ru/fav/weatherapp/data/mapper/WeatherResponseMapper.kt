package ru.fav.weatherapp.data.mapper

import ru.fav.weatherapp.data.remote.pojo.CurrentWeatherResponse
import ru.fav.weatherapp.domain.model.WeatherModel
import javax.inject.Inject

class WeatherResponseMapper @Inject constructor() {

    fun map(input: CurrentWeatherResponse?): WeatherModel {
        return input?.let {
            WeatherModel(
                currentTemp = it.mainData?.temp ?: -999F,
                windSpeed = it.wind?.speed ?: 0F,
                humidity = it.mainData?.humidity ?: 0,
                sunrise = it.sys?.sunrise ?: 0,
                sunset = it.sys?.sunset ?: 0,
                description = it.weather?.firstOrNull()?.description ?: "",
                icon = it.weather?.firstOrNull()?.icon ?: ""
            )
        } ?: WeatherModel()
    }
}

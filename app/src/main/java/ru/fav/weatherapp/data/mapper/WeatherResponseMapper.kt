package ru.fav.weatherapp.data.mapper

import ru.fav.weatherapp.data.remote.pojo.CurrentWeatherResponse
import ru.fav.weatherapp.domain.model.WeatherModel

class WeatherResponseMapper {

    fun map(input: CurrentWeatherResponse?): WeatherModel {
        return input?.let {
            WeatherModel(
                currentTemp = it.mainData?.temp ?: -999F,
                windSpeed = it.wind?.speed ?: 0F,
            )
        } ?: WeatherModel()
    }
}
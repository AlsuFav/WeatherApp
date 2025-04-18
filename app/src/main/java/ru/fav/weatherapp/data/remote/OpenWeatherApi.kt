package ru.fav.weatherapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import ru.fav.weatherapp.data.remote.pojo.CurrentWeatherResponse

interface OpenWeatherApi {

    @GET("weather")
    suspend fun getCurrentWeatherByCityName(
        @Query("q") city: String,
        @Query("units") units: String,
    ): CurrentWeatherResponse?

}
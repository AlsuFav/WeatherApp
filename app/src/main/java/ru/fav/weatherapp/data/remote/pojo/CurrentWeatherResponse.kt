package ru.fav.weatherapp.data.remote.pojo

import com.google.gson.annotations.SerializedName


class CurrentWeatherResponse (
    @SerializedName("main")
    val mainData: MainData? = null,
    @SerializedName("wind")
    val wind: Wind? = null,
    @SerializedName("sys")
    val sys: Sys? = null,
    @SerializedName("weather")
    val weather: List<Weather>? = null
)

class MainData (
    @SerializedName("temp")
    val temp: Float?,
    @SerializedName("feels_like")
    val feelslike: Float?,
    @SerializedName("pressure")
    val pressure: Float?,
    @SerializedName("humidity")
    val humidity: Int?
)

class Wind(
    @SerializedName("speed")
    val speed: Float?,
)

class Sys(
    @SerializedName("sunrise")
    val sunrise: Long?,
    @SerializedName("sunset")
    val sunset: Long?
)

class Weather(
    @SerializedName("description")
    val description: String?,
    @SerializedName("icon")
    val icon: String?
)
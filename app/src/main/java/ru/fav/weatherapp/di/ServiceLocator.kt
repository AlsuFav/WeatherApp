package ru.fav.weatherapp.di

import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.fav.weatherapp.BuildConfig
import ru.fav.weatherapp.BuildConfig.OPEN_WEATHER_BASE_URL
import ru.fav.weatherapp.data.mapper.WeatherResponseMapper
import ru.fav.weatherapp.data.remote.OpenWeatherApi
import ru.fav.weatherapp.data.remote.interceptors.AppIdInterceptor
import ru.fav.weatherapp.data.repository.WeatherRepositoryImpl
import ru.fav.weatherapp.domain.repository.WeatherRepository
import ru.fav.weatherapp.domain.usecase.GetWeatherByCityNameUseCase

object ServiceLocator {
    private var okHttpClient: OkHttpClient? = null
    private var openWeatherApi: OpenWeatherApi? = null
    private var weatherByCityNameUseCase: GetWeatherByCityNameUseCase? = null
    private var weatherRepository: WeatherRepository? = null

    private val mapper = WeatherResponseMapper()

    fun getOpenWeatherApi(): OpenWeatherApi {
        if (openWeatherApi == null) {
            val builder = OkHttpClient.Builder()
                .addInterceptor(AppIdInterceptor())
                .addInterceptor { chain ->
                    val url = chain.request().url.newBuilder()
                        .addQueryParameter("units", "metric")

                    val request = chain.request().newBuilder().url(url.build())
                    chain.proceed(request.build())
            }

            if (BuildConfig.DEBUG) {
                builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            }

            okHttpClient = builder.build()

            val retrofit = Retrofit.Builder()
                .baseUrl(OPEN_WEATHER_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            openWeatherApi = retrofit.create(OpenWeatherApi::class.java)
        }

        return openWeatherApi ?: throw IllegalArgumentException()
    }

    fun getWeatherByCityNameUseCase(): GetWeatherByCityNameUseCase {
        if (weatherByCityNameUseCase == null) {
            weatherByCityNameUseCase = GetWeatherByCityNameUseCase(
                weatherRepository = getWeatherRepository(),
                ioDispatcher = Dispatchers.IO,
            )
        }
        return weatherByCityNameUseCase ?: throw IllegalStateException()
    }

    private fun getWeatherRepository(): WeatherRepository {
        if (weatherRepository == null) {
            weatherRepository = WeatherRepositoryImpl(
                weatherApi = getOpenWeatherApi(),
                mapper = mapper,
            )
        }
        return weatherRepository ?: throw IllegalStateException()
    }
}
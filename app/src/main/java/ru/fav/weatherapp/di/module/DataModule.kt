package ru.fav.weatherapp.di.module

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.fav.weatherapp.BuildConfig.OPEN_WEATHER_BASE_URL
import ru.fav.weatherapp.data.remote.OpenWeatherApi


@Module
interface DataModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {

        return TODO("Provide the return value")
    }

    @Provides
    fun provideOpenWeatherApi(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory,
    ): OpenWeatherApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(OPEN_WEATHER_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()

        return retrofit.create(OpenWeatherApi::class.java)
    }
}
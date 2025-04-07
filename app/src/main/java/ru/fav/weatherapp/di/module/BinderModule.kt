package ru.fav.weatherapp.di.module

import dagger.Binds
import dagger.Module
import ru.fav.weatherapp.data.repository.SettingsRepositoryImpl
import ru.fav.weatherapp.data.repository.WeatherRepositoryImpl
import ru.fav.weatherapp.domain.repository.SettingsRepository
import ru.fav.weatherapp.domain.repository.WeatherRepository
import javax.inject.Singleton

@Module
interface BinderModule {

    @Binds
    @Singleton
    fun bindWeatherRepositoryToImpl(impl: WeatherRepositoryImpl): WeatherRepository

    @Binds
    @Singleton
    fun bindSettingsToImpl(impl: SettingsRepositoryImpl): SettingsRepository

}
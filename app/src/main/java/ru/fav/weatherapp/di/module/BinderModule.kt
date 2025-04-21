package ru.fav.weatherapp.di.module

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.fav.weatherapp.data.repository.SettingsRepositoryImpl
import ru.fav.weatherapp.data.repository.WeatherRepositoryImpl
import ru.fav.weatherapp.domain.repository.SettingsRepository
import ru.fav.weatherapp.domain.repository.WeatherRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface BinderModule {

    @Binds
    @Singleton
    fun bindWeatherRepositoryToImpl(impl: WeatherRepositoryImpl): WeatherRepository

    @Binds
    @Singleton
    fun bindSettingsToImpl(impl: SettingsRepositoryImpl): SettingsRepository

}
package ru.fav.weatherapp.data.repository

import android.content.Context
import android.os.Build
import ru.fav.weatherapp.domain.repository.SettingsRepository
import ru.fav.weatherapp.utils.usesMetricSystem
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val context: Context
) : SettingsRepository {
    override fun getTemperatureUnit(): String {
        return if (isMetricSystem(context)) "metric" else "imperial"
    }

    private fun isMetricSystem(context: Context): Boolean {
        val config = context.resources.configuration
        return config.locales[0].usesMetricSystem()
    }
}
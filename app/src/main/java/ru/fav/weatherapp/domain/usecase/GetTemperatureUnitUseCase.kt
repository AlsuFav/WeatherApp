package ru.fav.weatherapp.domain.usecase

import ru.fav.weatherapp.domain.repository.SettingsRepository
import javax.inject.Inject

class GetTemperatureUnitUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(): String {
        return settingsRepository.getTemperatureUnit()
    }
}
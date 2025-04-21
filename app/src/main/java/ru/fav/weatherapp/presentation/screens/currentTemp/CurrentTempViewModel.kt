package ru.fav.weatherapp.presentation.screens.currentTemp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.fav.weatherapp.domain.model.WeatherModel
import ru.fav.weatherapp.domain.usecase.GetTemperatureUnitUseCase
import ru.fav.weatherapp.domain.usecase.GetWeatherByCityNameUseCase
import javax.inject.Inject

@HiltViewModel
class CurrentTempViewModel @Inject constructor(
    private val getWeatherByCityNameUseCase: GetWeatherByCityNameUseCase,
    private val getUnitsUseCase: GetTemperatureUnitUseCase
) : ViewModel() {

    private val _currentWeatherFlow = MutableStateFlow<WeatherModel?>(null)
    val currentWeatherFlow = _currentWeatherFlow.asStateFlow()

    private val _loadingFlow = MutableStateFlow(false)
    val loadingFlow = _loadingFlow.asStateFlow()

    private val _errorFlow = MutableStateFlow(false)
    val errorFlow = _errorFlow.asStateFlow()

    private val _unitsFlow = MutableStateFlow<String>("")
    val unitsFlow = _unitsFlow.asStateFlow()

    init {
        viewModelScope.launch {
            val units = getUnitsUseCase()
            _unitsFlow.value = units
        }
    }

    fun getCurrentWeather(city: String) {
        _loadingFlow.value = true
        viewModelScope.launch {
            runCatching {
                val units =  _unitsFlow.value
                getWeatherByCityNameUseCase(city, units)
            }.onSuccess { weatherModel ->
                _currentWeatherFlow.value = weatherModel
                _loadingFlow.value = false
            }.onFailure {
                _loadingFlow.value = false
                _errorFlow.value = true
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
    }
}
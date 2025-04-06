package ru.fav.weatherapp.presentation.screens

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.*
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.fav.weatherapp.R
import ru.fav.weatherapp.presentation.base.BaseFragment
import ru.fav.weatherapp.databinding.FragmentCurrentTempBinding
import ru.fav.weatherapp.di.ServiceLocator
import ru.fav.weatherapp.domain.model.WeatherModel
import ru.fav.weatherapp.presentation.screens.ui.WeatherScreen

class CurrentTempFragment : BaseFragment(R.layout.fragment_current_temp) {
    private var viewBinding: FragmentCurrentTempBinding? = null
    private val getWeatherByCityNameUseCase = ServiceLocator.getWeatherByCityNameUseCase()
    private var weatherData by mutableStateOf<WeatherModel?>(null)
    private var isLoading by mutableStateOf(false)
    private var errorMessage by mutableStateOf<String?>(null)
    private var currentCity by mutableStateOf("Kazan") // Дефолтный город

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentCurrentTempBinding.bind(view)

        viewBinding?.composeContainerId?.setContent {
            WeatherScreen(
                weatherData = weatherData,
                isLoading = isLoading,
                errorMessage = errorMessage,
                currentCity = currentCity,
                onSearchClick = { city ->
                    currentCity = city
                    loadWeather(city) {
                            loadedData -> weatherData = loadedData
                        isLoading = false
                    }
                }
            )
        }
    }

    private fun loadWeather(
        city: String,
        onResult: (WeatherModel?) -> Unit
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            isLoading = true
            errorMessage = null

            runCatching {
                getWeatherByCityNameUseCase.invoke(city)
            }.onSuccess { weatherModel ->
                onResult(weatherModel)
            }.onFailure { e ->
                errorMessage = e.message ?: "Ошибка загрузки данных"
                onResult(null)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    companion object {
        const val MAIN_FRAGMENT_TAG = "MAIN_FRAGMENT_TAG"
    }
}
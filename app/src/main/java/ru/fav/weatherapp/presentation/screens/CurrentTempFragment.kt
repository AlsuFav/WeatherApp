package ru.fav.weatherapp.presentation.screens

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.fav.weatherapp.R
import ru.fav.weatherapp.presentation.base.BaseFragment
import ru.fav.weatherapp.databinding.FragmentCurrentTempBinding
import ru.fav.weatherapp.domain.exception.WrongTempException
import ru.fav.weatherapp.domain.model.WeatherModel
import ru.fav.weatherapp.domain.usecase.GetTemperatureUnitUseCase
import ru.fav.weatherapp.domain.usecase.GetWeatherByCityNameUseCase
import ru.fav.weatherapp.presentation.screens.ui.WeatherScreen
import ru.fav.weatherapp.utils.appComponent
import javax.inject.Inject

class CurrentTempFragment : BaseFragment(R.layout.fragment_current_temp) {
    private var viewBinding: FragmentCurrentTempBinding? = null

    @Inject
    lateinit var getWeatherByCityNameUseCase: GetWeatherByCityNameUseCase

    @Inject
    lateinit var getUnitsUseCase: GetTemperatureUnitUseCase

    private var weatherData by mutableStateOf<WeatherModel?>(null)
    private var isLoading by mutableStateOf(false)
    private var errorMessage by mutableStateOf<String?>(null)
    private var currentCity by mutableStateOf("Kazan") // Дефолтный город

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().appComponent().inject(fragment = this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentCurrentTempBinding.bind(view)

        val units = getUnitsUseCase()

        viewBinding?.composeContainerId?.setContent {
            WeatherScreen(
                weatherData = weatherData,
                isLoading = isLoading,
                errorMessage = errorMessage,
                currentCity = currentCity,
                units = units,
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
        val units = getUnitsUseCase()

        viewLifecycleOwner.lifecycleScope.launch {
            isLoading = true
            errorMessage = null

            runCatching {
                getWeatherByCityNameUseCase.invoke(city, units)
            }.onSuccess { weatherModel ->
                onResult(weatherModel)
            }.onFailure {
                when(it) {
                    is WrongTempException -> {
                        Toast.makeText(
                            requireContext(),
                            "Сервер вернул ошибку",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
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
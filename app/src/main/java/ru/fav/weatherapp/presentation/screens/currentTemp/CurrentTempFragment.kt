package ru.fav.weatherapp.presentation.screens.currentTemp

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.fav.weatherapp.R
import ru.fav.weatherapp.presentation.base.BaseFragment
import ru.fav.weatherapp.databinding.FragmentCurrentTempBinding
import javax.inject.Inject

@AndroidEntryPoint
class CurrentTempFragment : BaseFragment(R.layout.fragment_current_temp) {
    private var viewBinding: FragmentCurrentTempBinding? = null

    private val viewModel: CurrentTempViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentCurrentTempBinding.bind(view)

        viewBinding?.composeContainerId?.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                WeatherScreenWithViewModel(viewModel)
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


@Composable
private fun WeatherScreenWithViewModel(viewModel: CurrentTempViewModel) {
    val currentWeather by viewModel.currentWeatherFlow.collectAsState()
    val isLoading by viewModel.loadingFlow.collectAsState()
    val error by viewModel.errorFlow.collectAsState()
    val units by viewModel.unitsFlow.collectAsState()

    var cityInput by remember { mutableStateOf("") }

    WeatherScreen(
        weatherData = currentWeather,
        isLoading = isLoading,
        errorMessage = if (error) stringResource(R.string.network_error) else null,
        currentCity = cityInput,
        units = units,
        onSearchClick = { city ->
            cityInput = city
            viewModel.getCurrentWeather(city)
        }
    )
}
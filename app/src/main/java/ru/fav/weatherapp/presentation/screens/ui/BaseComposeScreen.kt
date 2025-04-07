package ru.fav.weatherapp.presentation.screens.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.fav.weatherapp.R
import ru.fav.weatherapp.domain.model.WeatherModel

@Composable
fun WeatherScreen(
    weatherData: WeatherModel?,
    isLoading: Boolean,
    errorMessage: String?,
    currentCity: String,
    units: String,
    onSearchClick: (String) -> Unit
) {
    var cityInput by remember { mutableStateOf(currentCity) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = cityInput,
                onValueChange = { cityInput = it },
                label = { Text("Введите город") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )

            IconButton(
                onClick = { onSearchClick(cityInput.trim()) },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(Icons.Default.Search, contentDescription = "Поиск")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            errorMessage != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colors.error
                    )
                }
            }
            weatherData != null -> {
                WeatherDisplay(
                    weatherData = weatherData,
                    units = units
                )
            }
        }
    }
}

@Composable
fun WeatherDisplay(
    weatherData: WeatherModel,
    units: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val tempUnit = when(units) {
            "metric" -> "°C"
            "imperial" -> "°F"
            else -> "K"
        }

        val windUnit = when(units) {
            "metric" -> "м/с"
            "imperial" -> "миль/ч"
            else -> "м/с"
        }

        WeatherInfoItem(
            label = "Температура",
            value = "${weatherData.currentTemp}$tempUnit",
            iconRes = R.drawable.ic_temp
        )

        WeatherInfoItem(
            label = "Скорость ветра",
            value = "${weatherData.windSpeed} $windUnit",
            iconRes = R.drawable.ic_wind
        )
    }
}

@Composable
fun WeatherInfoItem(label: String, value: String, iconRes: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            tint = MaterialTheme.colors.primary,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = label,
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
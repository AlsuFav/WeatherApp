package ru.fav.weatherapp.utils

import android.content.Context
import ru.fav.weatherapp.App
import ru.fav.weatherapp.di.components.AppComponent
import java.util.Locale

fun Context.appComponent(): AppComponent {
    return when(this) {
        is App -> appComponent
        else -> (this.applicationContext as App).appComponent()
    }
}

fun Locale.usesMetricSystem(): Boolean {
    return when (country.uppercase()) {
        "US", "LR", "MM" -> false
        else -> true
    }
}
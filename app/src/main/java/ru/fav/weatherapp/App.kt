package ru.fav.weatherapp

import android.app.Application
import ru.fav.weatherapp.di.components.AppComponent
import ru.fav.weatherapp.di.components.DaggerAppComponent

class App: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .build()
    }
}
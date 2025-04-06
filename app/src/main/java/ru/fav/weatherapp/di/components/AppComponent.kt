package ru.fav.weatherapp.di.components

import dagger.Component

@Component
interface AppComponent {

    interface Builder {
        fun build(): AppComponent
    }
}
package ru.fav.weatherapp.di.module

import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.fav.weatherapp.di.keys.ViewModelKey
import ru.fav.weatherapp.presentation.screens.currentTemp.CurrentTempViewModel

@Module
class PresentationModule {

    @Provides
    @IntoMap
    @ViewModelKey(CurrentTempViewModel::class)
    fun bindCurrentTempViewModel(
        vm: CurrentTempViewModel
    ): ViewModel = vm
}
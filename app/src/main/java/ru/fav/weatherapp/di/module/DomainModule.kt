package ru.fav.weatherapp.di.module

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import ru.fav.weatherapp.di.qualifier.IoDispatchers

@Module
class DomainModule {

    @Provides
    @IoDispatchers
    fun provideIoDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}
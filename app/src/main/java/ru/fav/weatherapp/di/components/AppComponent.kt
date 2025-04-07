package ru.fav.weatherapp.di.components

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.fav.weatherapp.di.module.BinderModule
import ru.fav.weatherapp.di.module.DataModule
import ru.fav.weatherapp.di.module.DomainModule
import ru.fav.weatherapp.presentation.MainActivity
import ru.fav.weatherapp.presentation.screens.CurrentTempFragment
import javax.inject.Singleton

@Component(
    modules = [
        DataModule::class,
        DomainModule::class,
        BinderModule::class,
    ]
)
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun provideContext(ctx: Context): Builder

        fun build(): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: CurrentTempFragment)
}
package ru.fav.weatherapp.presentation

import android.os.Bundle
import ru.fav.weatherapp.R
import ru.fav.weatherapp.presentation.base.BaseActivity
import ru.fav.weatherapp.presentation.base.NavigationAction
import ru.fav.weatherapp.databinding.ActivityMainBinding
import ru.fav.weatherapp.presentation.screens.currentTemp.CurrentTempFragment
import ru.fav.weatherapp.utils.appComponent

class MainActivity : BaseActivity() {

    override val mainContainerId = R.id.main_fragment_container

    private var viewBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent().inject(activity = this)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding?.root)

        navigate(
            destination = CurrentTempFragment(),
            destinationTag = CurrentTempFragment.Companion.MAIN_FRAGMENT_TAG,
            action = NavigationAction.ADD,
            isAddToBackStack = false
        )
    }

    override fun onDestroy() {
        viewBinding = null
        super.onDestroy()
    }
}
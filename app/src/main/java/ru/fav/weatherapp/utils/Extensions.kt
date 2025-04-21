package ru.fav.weatherapp.utils

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.fav.weatherapp.App
import java.util.Locale
import kotlinx.coroutines.flow.Flow
import ru.fav.weatherapp.presentation.MainActivity


fun Locale.usesMetricSystem(): Boolean {
    return when (this.country.uppercase()) {
        "US", "LR", "MM" -> false
        else -> true
    }
}

//fun <T> Flow<T>.observe(fragment: Fragment, block: suspend (T) -> Unit) {
//    fragment.lifecycleScope.launch {
//        fragment.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
//            this@observe.collect {block.invoke(it)}
//        }
//    }
//}

//inline fun <T> Flow<T>.observe(
//    lifecycleOwner: LifecycleOwner,
//    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
//    crossinline block: (T) -> Unit
//): Job {
//    return lifecycleOwner.lifecycleScope.launch {
//        lifecycleOwner.repeatOnLifecycle(lifecycleState) {
//            collect {
//                block.invoke(it)
//            }
//        }
//    }
//}
//
//inline fun <T> Flow<T>.observe(
//    activity: FragmentActivity,
//    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
//    crossinline block: (T) -> Unit
//): Job {
//    return activity.lifecycleScope.launch {
//        activity.repeatOnLifecycle(lifecycleState) {
//            collect {
//                block.invoke(it)
//            }
//        }
//    }
//}

package ru.fav.weatherapp.data.remote.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import ru.fav.weatherapp.BuildConfig

class AppIdInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url.newBuilder()
            .addQueryParameter("appid", BuildConfig.openWeatherApiKey)

        val request = chain.request().newBuilder().url(url.build())

        return chain.proceed(request.build())
    }
}
package ru.fav.weatherapp.di.module

import android.annotation.SuppressLint
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.fav.weatherapp.BuildConfig.OPEN_WEATHER_BASE_URL
import ru.fav.weatherapp.data.mapper.WeatherResponseMapper
import ru.fav.weatherapp.data.remote.OpenWeatherApi
import ru.fav.weatherapp.data.remote.interceptors.AppIdInterceptor
import ru.fav.weatherapp.data.remote.interceptors.LangInterceptor
import ru.fav.weatherapp.data.repository.WeatherRepositoryImpl
import ru.fav.weatherapp.domain.repository.WeatherRepository
import java.security.SecureRandom
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import java.security.cert.X509Certificate
import javax.inject.Singleton


@Module
class DataModule {

    @Provides
    fun provideOkHttpClient(
        appIdInterceptor: AppIdInterceptor,
        langInterceptor: LangInterceptor
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        try {
            val trustAllCerts: Array<TrustManager> = arrayOf(@SuppressLint("CustomX509TrustManager")
            object : X509TrustManager {
                @SuppressLint("TrustAllX509TrustManager")
                override fun checkClientTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

                @SuppressLint("TrustAllX509TrustManager")
                override fun checkServerTrusted(
                    chain: Array<out X509Certificate?>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<out X509Certificate?> = arrayOf()
            })

            val sslContext = SSLContext.getInstance ("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            val sslSocketFactory = sslContext.socketFactory
            if (trustAllCerts.isNotEmpty() && trustAllCerts.first() is X509TrustManager) {
                okHttpClient.sslSocketFactory(
                    sslSocketFactory,
                    trustAllCerts.first() as X509TrustManager
                )
                okHttpClient.hostnameVerifier { _, _ -> true }
            }

            return okHttpClient
                .addInterceptor(appIdInterceptor)
                .addInterceptor(langInterceptor)
                .build()
        } catch (e: Exception) {
            return okHttpClient.addInterceptor(appIdInterceptor).build()
        }
    }

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideOpenWeatherApi(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory,
    ): OpenWeatherApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(OPEN_WEATHER_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()

        return retrofit.create(OpenWeatherApi::class.java)
    }
}
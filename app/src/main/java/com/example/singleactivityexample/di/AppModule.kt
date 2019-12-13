package com.example.singleactivityexample.di

import com.example.singleactivityexample.BuildConfig
import com.example.singleactivityexample.domain.NewsApi
import com.example.singleactivityexample.domain.NewsRepository
import com.example.singleactivityexample.navigation.Navigator
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

private const val BASE_HOST_NAME = "base_host"

val appModule = module {
    single(named(BASE_HOST_NAME)) { "https://jsonplaceholder.typicode.com" }

    single {
        Moshi.Builder()
            .build()
    }

    single<Converter.Factory> { MoshiConverterFactory.create(get()) }

    single {
        HttpLoggingInterceptor(
            HttpLoggingInterceptor.Logger { message ->
                if (BuildConfig.DEBUG)
                    Timber.i(message)
            })
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    single {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(get<String>(named(BASE_HOST_NAME)))
            .client(get())
            .addConverterFactory(get())
            .build()
    }

    single<NewsApi> {
        get<Retrofit>().create(NewsApi::class.java)
    }

    single { NewsRepository(get()) }

    single { Navigator() }
}
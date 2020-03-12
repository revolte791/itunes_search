package com.hwx.itunessearchbox.di.modules


import dagger.Lazy
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hwx.itunessearchbox.BuildConfig
import com.hwx.itunessearchbox.api.ITunesApi
import com.hwx.itunessearchbox.api.repository.ITunesRepository
import com.hwx.itunessearchbox.api.repository.ITunesRepositoryImpl
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ITunesApiModule {

    private val timeOut = 20L
    @Provides
    @Singleton
    fun provideClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            readTimeout(timeOut, TimeUnit.SECONDS)
            connectTimeout(timeOut, TimeUnit.SECONDS)
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                    addInterceptor(this)
                }
            }
        }.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: Lazy<OkHttpClient>, gson: Gson): Retrofit {
        return Retrofit.Builder()
              .baseUrl("https://itunes.apple.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .callFactory(client.get())
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): ITunesApi {
        return retrofit.create(ITunesApi::class.java)
    }


    @Provides
    @Singleton
    fun provideApiRepository(iTunesApi: ITunesApi): ITunesRepository = ITunesRepositoryImpl(iTunesApi)

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }
}
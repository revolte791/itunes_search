package com.hwx.itunessearchbox.di.modules

import android.content.Context
import com.hwx.itunessearchbox.provider.ConnectionStateProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule constructor(private val context: Context) {

    @Provides
    @Singleton
    fun provideAppContext() = context

    @Provides
    @Singleton
    fun provideConnectionStateProvider(mContext: Context) = ConnectionStateProvider(mContext)

}
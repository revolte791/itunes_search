package com.hwx.itunessearchbox

import com.hwx.itunessearchbox.di.component.DaggerAppComponent
import com.hwx.itunessearchbox.di.modules.AppModule
import com.hwx.itunessearchbox.di.modules.ITunesApiModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class ITunesApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
                .builder()
                .application(this)
                .appModule(AppModule(applicationContext))
                .iTunesApiModule(ITunesApiModule())
                .build()
    }
}
package com.hwx.itunessearchbox.di.component

import android.app.Application
import com.hwx.itunessearchbox.ITunesApplication
import com.hwx.itunessearchbox.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Component(modules = [
    AndroidInjectionModule::class,
    ActivityBuilderModule::class,
    AppModule::class,
    ITunesApiModule::class,
    HomeModule::class,
    ViewModelFactoryModule::class
])
@Singleton
interface AppComponent: AndroidInjector<ITunesApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder
        fun appModule(appModule: AppModule): Builder
        fun iTunesApiModule(iTunesApiModule: ITunesApiModule): Builder


        fun build(): AppComponent
    }

    override fun inject(app: ITunesApplication)
}
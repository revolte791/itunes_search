package com.hwx.itunessearchbox.di.modules

import com.hwx.itunessearchbox.ui.MainActivity
import com.hwx.itunessearchbox.di.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBuilderModule {

    @ActivityScope
    @ContributesAndroidInjector(
        modules = [
            ActivityModule::class,
            FragmentBuilderModule::class
        ]
    )
    fun contributeMainActivity(): MainActivity
}
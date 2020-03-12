package com.hwx.itunessearchbox.di.modules

import androidx.lifecycle.ViewModel
import com.hwx.itunessearchbox.ui.fragment.HomeFragment
import com.hwx.itunessearchbox.viewmodel.HomeViewModel
import com.hwx.itunessearchbox.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class HomeModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel


    @ContributesAndroidInjector
    abstract fun contributesHomeFragment(): HomeFragment



}
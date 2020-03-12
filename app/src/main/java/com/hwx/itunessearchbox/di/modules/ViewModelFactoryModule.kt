package com.hwx.itunessearchbox.di.modules

import androidx.lifecycle.ViewModelProvider
import com.hwx.itunessearchbox.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
interface ViewModelFactoryModule {

  @Binds
  fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}

package com.hwx.itunessearchbox.di.modules

import com.hwx.itunessearchbox.ui.fragment.AlbumDetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentBuilderModule {

  @ContributesAndroidInjector(
    modules = [AlbumDetailsArgsModule::class, AlbumDetailsViewModelsModule::class]
  )
  fun contributesAlbumDetailsFragment(): AlbumDetailsFragment

}
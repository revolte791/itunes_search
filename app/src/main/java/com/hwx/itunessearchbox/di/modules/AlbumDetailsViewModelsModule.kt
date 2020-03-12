package com.hwx.itunessearchbox.di.modules

import androidx.lifecycle.ViewModel
import com.hwx.itunessearchbox.di.ViewModelKey
import com.hwx.itunessearchbox.viewmodel.AlbumDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class AlbumDetailsViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(AlbumDetailsViewModel::class)
    abstract fun bindAlbumDetailsViewModel(albumDetailsViewModel: AlbumDetailsViewModel): ViewModel


}
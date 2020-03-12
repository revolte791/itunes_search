package com.hwx.itunessearchbox.di.modules

import com.hwx.itunessearchbox.di.modules.AlbumDetailsArgsModule.AlbumDetailsArgsModuleQualifier.ALBUM_ID
import com.hwx.itunessearchbox.ui.fragment.AlbumDetailsFragment
import com.hwx.itunessearchbox.ui.fragment.AlbumDetailsFragmentArgs
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class AlbumDetailsArgsModule  {
    object AlbumDetailsArgsModuleQualifier {
        const val ALBUM_ID = "ALBUM_ID"
    }

    @Provides
    @Named(ALBUM_ID)
    fun provideReceiptId(fragment: AlbumDetailsFragment): Long {
        return AlbumDetailsFragmentArgs.fromBundle(fragment.requireArguments()).albumId
    }
}

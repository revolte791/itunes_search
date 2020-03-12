package com.hwx.itunessearchbox.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hwx.itunessearchbox.NetworkResult
import com.hwx.itunessearchbox.api.entity.ITunesResponse
import com.hwx.itunessearchbox.api.repository.ITunesRepository
import com.hwx.itunessearchbox.di.modules.AlbumDetailsArgsModule
import com.hwx.itunessearchbox.provider.ConnectionStateProvider
import kotlinx.coroutines.*
import java.lang.Exception

import javax.inject.Inject
import javax.inject.Named

/**
 * ViewModel для фрагмента со списком трэков
 */
class AlbumDetailsViewModel  @Inject constructor(
    @Named(AlbumDetailsArgsModule.AlbumDetailsArgsModuleQualifier.ALBUM_ID) private val albumId: Long,
    private val connectionStateProvider: ConnectionStateProvider,
    private val iTunesRepository: ITunesRepository
) : ViewModel() {

    private var coroutineJob: Job? = null

    private var mlvTracks = MutableLiveData<NetworkResult<ITunesResponse>>()
    val lvTracks: LiveData<NetworkResult<ITunesResponse>> = mlvTracks

    private val mlvShowError = MutableLiveData<String>()
    val lvShowError: LiveData<String> = mlvShowError

    fun mlvShowErrorCompleted() {
        mlvShowError.value = ""
    }


    @UseExperimental(InternalCoroutinesApi::class)
    fun getTracks() {
        if (coroutineJob?.isActive == true) {
            coroutineJob!!.cancel()
        }
        coroutineJob = viewModelScope.launch(Dispatchers.IO) {
            val isConnected = connectionStateProvider.isConnectingToInternet()
            if (isConnected) {
                try {
                    val result = iTunesRepository.getTracks("song", albumId)
                    mlvTracks.postValue(result)
                } catch (e: Exception) {
                    mlvShowError.postValue("Ошибка при получении данных")
                }
            } else {
                mlvShowError.postValue("Остутствует подключение к интернету")
            }
        }
    }
}
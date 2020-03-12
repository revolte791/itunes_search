package com.hwx.itunessearchbox.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hwx.itunessearchbox.NetworkResult
import com.hwx.itunessearchbox.api.entity.ITunesResponse
import com.hwx.itunessearchbox.api.repository.ITunesRepository
import com.hwx.itunessearchbox.provider.ConnectionStateProvider
import kotlinx.coroutines.*
import timber.log.Timber
import java.lang.Exception

import javax.inject.Inject

/**
 * ViewModel для фрагмента со списком альбомов
 */
class HomeViewModel  @Inject constructor(
    private val connectionStateProvider: ConnectionStateProvider,
    private val iTunesRepository: ITunesRepository
) : ViewModel() {

    private var coroutineJob: Job? = null

    private var mlvAlbums = MutableLiveData<NetworkResult<ITunesResponse>>()
    val lvAlbums: LiveData<NetworkResult<ITunesResponse>> = mlvAlbums

    private val mlvShowError = MutableLiveData<String>()
    val lvShowError: LiveData<String> = mlvShowError

    private val mlvOnAlbumClick = MutableLiveData<Long?>()
    val lvOnAlbumClick: LiveData<Long?> = mlvOnAlbumClick

    fun onItemClick(id: Long?) {
        mlvOnAlbumClick.value = id
    }

    fun navigateToAlbumDetailsCompleted() {
        mlvOnAlbumClick.value = null
    }

    fun mlvShowErrorCompleted() {
        mlvShowError.value = ""
    }



    fun filterAlbumsByName(albumName: String) {
        if (albumName.isNotEmpty())
            getAlbumsApiCall(albumName)
    }

    @UseExperimental(InternalCoroutinesApi::class)
    private fun getAlbumsApiCall(searchText: String) {
        if (coroutineJob?.isActive == true) {
            coroutineJob!!.cancel()
        }
        coroutineJob = viewModelScope.launch(Dispatchers.IO) {
            val isConnected = connectionStateProvider.isConnectingToInternet()
            if (isConnected) {

                try {
                    val result = iTunesRepository.searchAlbums("album", searchText, 20)
                    mlvAlbums.postValue(result)
                } catch (e: Exception) {
                    mlvShowError.postValue("Ошибка при получении данных")
                }
            } else {
                mlvShowError.postValue("Остутствует подключение к интернету")
            }
        }
    }
}
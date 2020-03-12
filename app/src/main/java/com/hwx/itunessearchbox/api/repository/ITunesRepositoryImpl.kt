package com.hwx.itunessearchbox.api.repository

import com.hwx.itunessearchbox.NetworkError
import com.hwx.itunessearchbox.NetworkResult
import com.hwx.itunessearchbox.NetworkSuccess
import com.hwx.itunessearchbox.api.ITunesApi
import com.hwx.itunessearchbox.api.entity.ITunesResponse
import com.hwx.itunessearchbox.exception.NoResponseException

class ITunesRepositoryImpl(
    private val iTunesApi: ITunesApi
) : ITunesRepository {

    override suspend fun searchAlbums(entity: String,
                                      term: String,
                                      limit: Int
    ): NetworkResult<ITunesResponse> {
        return iTunesApi.searchAlbum(entity, term, limit).run {
            if (isSuccessful && body() != null) {
                NetworkSuccess(body()!!)
            } else {
                NetworkError(
                    NoResponseException(errorBody().toString())
                )
            }
        }
    }

    override suspend fun getTracks(entity: String, id: Long): NetworkResult<ITunesResponse> {
        return iTunesApi.lookup(entity, id).run {
            if (isSuccessful && body() != null) {
                NetworkSuccess(body()!!)
            } else {
                NetworkError(
                    NoResponseException(errorBody().toString())
                )
            }
        }
    }

}
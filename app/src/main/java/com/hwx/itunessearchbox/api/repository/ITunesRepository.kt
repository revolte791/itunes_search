package com.hwx.itunessearchbox.api.repository

import com.hwx.itunessearchbox.NetworkResult
import com.hwx.itunessearchbox.api.entity.ITunesResponse

interface ITunesRepository {
    suspend fun searchAlbums(entity: String,
                             term: String,
                             limit: Int
    ): NetworkResult<ITunesResponse>

    suspend fun getTracks(entity: String,
                          id: Long
    ): NetworkResult<ITunesResponse>
}
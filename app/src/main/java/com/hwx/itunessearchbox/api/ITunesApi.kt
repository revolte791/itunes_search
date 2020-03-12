package com.hwx.itunessearchbox.api

import androidx.annotation.WorkerThread
import com.hwx.itunessearchbox.api.entity.ITunesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Интерфейс взаимодействия с itunes api
 */
interface ITunesApi {

    /**
     * Запрос для поиска альбомов по названию
     */
    @WorkerThread
    @GET("search")
    suspend fun searchAlbum(
        @Query("entity") entity: String,
        @Query("term") term: String,
        @Query("limit") limit: Int
    ): Response<ITunesResponse>

    /**
     * Запрос для получения содержимого альбома
     * https://itunes.apple.com/lookup?id=1443471726&sort=recent&entity=song
     */
    @WorkerThread
    @GET("lookup")
    suspend fun lookup(
        @Query("entity") entity: String,
        @Query("id") id: Long
    ): Response<ITunesResponse>
}
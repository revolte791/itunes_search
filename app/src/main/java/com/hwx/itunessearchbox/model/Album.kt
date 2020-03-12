package com.hwx.itunessearchbox.model

import java.util.*

/**
 * Бизнес сущность. Модель альбома
 */
data class Album(
    var collectionId: Long,
    var artistName: String,
    var artistViewUrl: String?,

    var collectionName: String,

    var artworkUrl60: String,
    var artworkUrl100: String,

    var country: String,
    var releaseDate: Date,
    var primaryGenreName: String
)
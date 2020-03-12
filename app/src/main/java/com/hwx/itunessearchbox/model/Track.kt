package com.hwx.itunessearchbox.model

import java.util.*

/**
 * Бизнес сущность. Модель песни(трэка)
 */
data class Track(
    var trackName: String,
    var trackNumber: Int,
    var trackTimeMillis: Int,
    var artworkUrl30: String,
    var artworkUrl100: String,
    var releaseDate: Date
)
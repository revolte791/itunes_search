package com.hwx.itunessearchbox.api.entity

import com.hwx.itunessearchbox.Constants.ITUNES_DATE_FORMAT
import com.hwx.itunessearchbox.model.Album
import com.hwx.itunessearchbox.model.Track

/**
 * Сущность поиска от iTunes API
 */
data class SearchItem(

    var wrapperType: String, //collection, track
    var collectionId: Long,
    var artistName: String,
    var artistViewUrl: String?,
    var collectionName: String,

    var artworkUrl30: String?,
    var artworkUrl60: String?,
    var artworkUrl100: String,

    var country: String,
    var releaseDate: String,
    var primaryGenreName: String,

    var trackName: String?,
    var trackNumber: Int?,
    var trackTimeMillis: Int?

) {
    fun toAlbum(): Album {
        val relDate = ITUNES_DATE_FORMAT.parse(releaseDate)!!
        return Album(collectionId, artistName, artistViewUrl, collectionName, artworkUrl60!!, artworkUrl100, country, relDate, primaryGenreName)
    }

    fun toTrack(): Track {
        val relDate = ITUNES_DATE_FORMAT.parse(releaseDate)!!
        return Track(trackName!!, trackNumber!!, trackTimeMillis!!, artworkUrl30!!, artworkUrl100, relDate)
    }
}
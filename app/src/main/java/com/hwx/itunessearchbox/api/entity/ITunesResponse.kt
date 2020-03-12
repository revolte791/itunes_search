package com.hwx.itunessearchbox.api.entity

/**
 * Базовая сущность ответа от ITunes APi
 */
data class ITunesResponse(

    var resultCount: Int,
    var results: List<SearchItem>

)
package com.willaapps.shop.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class WordDto(
    val sentence: String,
    val wordPl: String,
    val wordEng: String,
    val setId: String,
    val groupNumber: Int,
    val id: String
)

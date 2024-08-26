package com.willaapps.shop.data

import kotlinx.serialization.Serializable

@Serializable
data class WordSetResponse(
    val sets: List<WordSetDto>
)

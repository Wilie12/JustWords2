package com.willaapps.shop.data.response

import com.willaapps.shop.data.dto.WordDto
import kotlinx.serialization.Serializable

@Serializable
data class WordsResponse(
    val words: List<WordDto>
)

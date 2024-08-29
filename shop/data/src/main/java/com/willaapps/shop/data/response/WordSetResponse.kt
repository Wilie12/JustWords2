package com.willaapps.shop.data.response

import com.willaapps.shop.data.dto.WordSetDto
import kotlinx.serialization.Serializable

@Serializable
data class WordSetResponse(
    val sets: List<WordSetDto>
)

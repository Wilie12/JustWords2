package com.willaapps.shop.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class WordSetDto(
    val name: String,
    val bookId: String,
    val numberOfGroups: Int,
    val id: String
)

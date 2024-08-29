package com.willaapps.shop.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class BookDto(
    val name: String,
    val color: Int,
    val id: String
)

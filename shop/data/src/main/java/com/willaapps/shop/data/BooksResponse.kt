package com.willaapps.shop.data

import kotlinx.serialization.Serializable

@Serializable
data class BooksResponse(
    val books: List<BookDto>
)

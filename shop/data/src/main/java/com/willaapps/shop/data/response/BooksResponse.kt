package com.willaapps.shop.data.response

import com.willaapps.shop.data.dto.BookDto
import kotlinx.serialization.Serializable

@Serializable
data class BooksResponse(
    val books: List<BookDto>
)

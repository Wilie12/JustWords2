package com.willaapps.shop.domain.model

import com.willaapps.core.domain.word.Book

data class ShopBook(
    val book: Book = Book(
        name = "",
        bookId = "",
        color = 0
    ),
    val setNames: List<String> = emptyList()
)

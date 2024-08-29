package com.willaapps.shop.presentation.shop_set

import com.willaapps.core.domain.word.Book
import com.willaapps.shop.domain.model.ShopWordSet

data class ShopSetState(
    val shopWordSets: List<ShopWordSet> = emptyList(),
    val book: Book = Book(
        name = "",
        bookId = "",
        color = 0
    ),
    val isDownloading: Boolean = false,
    val isLoading: Boolean = false
)

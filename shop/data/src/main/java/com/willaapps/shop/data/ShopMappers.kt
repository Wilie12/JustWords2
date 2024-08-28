package com.willaapps.shop.data

import com.willaapps.core.domain.word.Book
import com.willaapps.core.domain.word.WordSet
import com.willaapps.shop.domain.ShopWordSet

fun BookDto.toBook(): Book {
    return Book(
        name = name,
        color = color,
        bookId = id
    )
}

fun WordSetDto.toWordSet(): WordSet {
    return WordSet(
        name = name,
        bookId = bookId,
        numberOfGroups = numberOfGroups,
        id = id
    )
}

fun WordSet.toShopWordSet(isDownloaded: Boolean): ShopWordSet {
    return ShopWordSet(
        name = name,
        bookId = bookId,
        numberOfGroups = numberOfGroups,
        id = id,
        isDownloaded = isDownloaded
    )
}
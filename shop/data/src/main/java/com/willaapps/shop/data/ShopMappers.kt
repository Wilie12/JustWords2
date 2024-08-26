package com.willaapps.shop.data

import com.willaapps.core.domain.word.Book
import com.willaapps.core.domain.word.WordSet

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
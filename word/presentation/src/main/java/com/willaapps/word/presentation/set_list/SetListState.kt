package com.willaapps.word.presentation.set_list

import com.willaapps.core.domain.word.Book
import com.willaapps.core.domain.word.WordSet

data class SetListState(
    val sets: List<WordSet> = emptyList(),
    val book: Book = Book(
        name = "",
        bookId = "",
        color = 0
    )
)

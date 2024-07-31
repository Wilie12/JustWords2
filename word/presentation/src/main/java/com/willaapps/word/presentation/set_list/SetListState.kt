package com.willaapps.word.presentation.set_list

import com.willaapps.word.domain.Book
import com.willaapps.word.domain.WordSet

data class SetListState(
    val sets: List<WordSet> = emptyList(),
    val book: Book = Book(
        name = "",
        bookId = "",
        color = 0
    )
)

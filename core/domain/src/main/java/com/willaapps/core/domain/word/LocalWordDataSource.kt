package com.willaapps.core.domain.word

import kotlinx.coroutines.flow.Flow

interface LocalWordDataSource {

    fun getBooks(): Flow<List<Book>>
    fun getBookById(bookId: String): Flow<Book>
    fun getWordSets(bookId: String): Flow<List<WordSet>>
    fun getSelectedWordGroup(
        bookId: String,
        setId: String,
        groupNumber: Int
    ): Flow<List<Word>>
}
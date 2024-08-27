package com.willaapps.core.domain.word

import kotlinx.coroutines.flow.Flow

interface LocalWordDataSource {

    fun getBooks(): Flow<List<Book>>
    fun getBookById(bookId: String): Flow<Book>
    fun getWordSetsById(bookId: String): Flow<List<WordSet>>
    fun getSetById(setId: String): Flow<WordSet>
    fun getSelectedWordGroup(setId: String, groupNumber: Int): Flow<List<Word>>
}
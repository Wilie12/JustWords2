package com.willaapps.core.domain.word

import kotlinx.coroutines.flow.Flow

interface LocalWordRepository {
    // TODO - delete
    fun getLocalBooks(): Flow<List<Book>>
    fun getLocalBookById(bookId: String): Flow<Book>
    fun getLocalWordSetsById(bookId: String): Flow<List<WordSet>>
    fun getLocalSetById(setId: String): Flow<WordSet>
    fun getSelectedWordGroup(setId: String, groupNumber: Int): Flow<List<Word>>
}
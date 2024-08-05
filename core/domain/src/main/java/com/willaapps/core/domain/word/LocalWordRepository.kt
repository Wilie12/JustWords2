package com.willaapps.core.domain.word

import kotlinx.coroutines.flow.Flow

interface LocalWordRepository {

    fun getLocalBooks(): Flow<List<Book>>
    fun getLocalBookById(bookId: String): Flow<Book>
    fun getLocalWordSets(bookId: String): Flow<List<WordSet>>
}
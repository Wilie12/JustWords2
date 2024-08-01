package com.willaapps.core.domain.word

import kotlinx.coroutines.flow.Flow

interface LocalWordDataSource {

    fun getBooks(): Flow<List<Book>>

    fun getWordSets(bookId: String): Flow<List<WordSet>>

}
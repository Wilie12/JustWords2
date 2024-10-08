package com.willaapps.core.domain.word

import com.willaapps.core.domain.util.DataError
import com.willaapps.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface LocalWordDataSource {
    fun getBooks(): Flow<List<Book>>
    fun getBookById(bookId: String): Flow<Book>
    fun getWordSetsById(bookId: String): Flow<List<WordSet>>
    fun getSetById(setId: String): Flow<WordSet>
    fun getSelectedWordGroup(setId: String, groupNumber: Int): Flow<List<Word>>
    suspend fun insertWords(words: List<Word>): Result<List<String>, DataError.Local>
    suspend fun insertWordSet(wordSet: WordSet): Result<String, DataError.Local>
    suspend fun insertBook(book: Book): Result<String, DataError.Local>
    suspend fun deleteAll()
}
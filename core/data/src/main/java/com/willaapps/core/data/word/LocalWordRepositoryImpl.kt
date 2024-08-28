package com.willaapps.core.data.word

import com.willaapps.core.domain.word.Book
import com.willaapps.core.domain.word.LocalWordDataSource
import com.willaapps.core.domain.word.LocalWordRepository
import com.willaapps.core.domain.word.Word
import com.willaapps.core.domain.word.WordSet
import kotlinx.coroutines.flow.Flow

class LocalWordRepositoryImpl(
    private val localWordDataSource: LocalWordDataSource
): LocalWordRepository {

    // TODO - delete
    override fun getLocalBooks(): Flow<List<Book>> {
        return localWordDataSource.getBooks()
    }

    override fun getLocalBookById(bookId: String): Flow<Book> {
        return localWordDataSource.getBookById(bookId)
    }

    override fun getLocalWordSetsById(bookId: String): Flow<List<WordSet>> {
        return localWordDataSource.getWordSetsById(bookId)
    }

    override fun getLocalSetById(setId: String): Flow<WordSet> {
        return localWordDataSource.getSetById(setId)
    }

    override fun getSelectedWordGroup(setId: String, groupNumber: Int): Flow<List<Word>> {
        return localWordDataSource.getSelectedWordGroup(setId, groupNumber)
    }
}
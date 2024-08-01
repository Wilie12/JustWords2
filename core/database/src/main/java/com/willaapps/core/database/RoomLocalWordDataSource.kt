package com.willaapps.core.database

import com.willaapps.core.database.dao.WordDao
import com.willaapps.core.database.mappers.toBook
import com.willaapps.core.database.mappers.toWordSet
import com.willaapps.core.domain.word.Book
import com.willaapps.core.domain.word.LocalWordDataSource
import com.willaapps.core.domain.word.WordSet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalWordDataSource(
    private val wordDao: WordDao
): LocalWordDataSource {

    override fun getBooks(): Flow<List<Book>> {
        return wordDao.getBookList()
            .map { bookEntity ->
                bookEntity.map { it.toBook() }
            }
    }

    override fun getWordSets(bookId: String): Flow<List<WordSet>> {
        return wordDao.getSetList(bookId)
            .map { setEntity ->
                setEntity.map { it.toWordSet() }
            }
    }
}
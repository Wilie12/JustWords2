package com.willaapps.core.database

import android.database.sqlite.SQLiteFullException
import com.willaapps.core.database.dao.WordDao
import com.willaapps.core.database.mappers.toBook
import com.willaapps.core.database.mappers.toBookEntity
import com.willaapps.core.database.mappers.toSetEntity
import com.willaapps.core.database.mappers.toWord
import com.willaapps.core.database.mappers.toWordEntity
import com.willaapps.core.database.mappers.toWordSet
import com.willaapps.core.domain.util.DataError
import com.willaapps.core.domain.util.Result
import com.willaapps.core.domain.word.Book
import com.willaapps.core.domain.word.LocalWordDataSource
import com.willaapps.core.domain.word.Word
import com.willaapps.core.domain.word.WordSet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalWordDataSource(
    private val wordDao: WordDao
) : LocalWordDataSource {

    override fun getBooks(): Flow<List<Book>> {
        return wordDao.getBookList().map { bookEntity -> bookEntity.map { it.toBook() } }
    }

    override fun getBookById(bookId: String): Flow<Book> {
        return wordDao.getBookById(bookId).map { bookEntity -> bookEntity.toBook() }
    }

    override fun getWordSetsById(bookId: String): Flow<List<WordSet>> {
        return wordDao.getSetList(bookId).map { setEntity -> setEntity.map { it.toWordSet() } }
    }

    override fun getSetById(setId: String): Flow<WordSet> {
        return wordDao.getSetById(setId).map { setEntity -> setEntity.toWordSet() }
    }

    override fun getSelectedWordGroup(setId: String, groupNumber: Int): Flow<List<Word>> {
        return wordDao
            .getSelectedWordGroup(setId, groupNumber)
            .map { wordEntities -> wordEntities.map { wordEntity -> wordEntity.toWord() } }
    }

    override suspend fun insertWords(words: List<Word>): Result<List<String>, DataError.Local> {
        return try {
            val wordsEntities = words.map { it.toWordEntity() }
            wordDao.insertWords(wordsEntities)
            Result.Success(wordsEntities.map { it.id })
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun insertWordSet(wordSet: WordSet): Result<String, DataError.Local> {
        return try {
            val setEntity = wordSet.toSetEntity()
            wordDao.insertSet(setEntity)
            Result.Success(setEntity.id)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun insertBook(book: Book): Result<String, DataError.Local> {
        return try {
            val bookEntity = book.toBookEntity()
            wordDao.insertBook(bookEntity)
            Result.Success(bookEntity.id)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }
}
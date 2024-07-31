package com.willaapps.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.willaapps.core.database.entity.BookEntity
import com.willaapps.core.database.entity.SetEntity
import com.willaapps.core.database.entity.WordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Query("SELECT * FROM WordEntity " +
            "WHERE bookId = :bookId " +
            "AND setId = :setId " +
            "AND groupNumber = :groupNumber")
    fun getSelectedWordGroup(
        bookId: String,
        setId: String,
        groupNumber: String
    ): Flow<List<WordEntity>>

    @Query("SELECT * FROM SetEntity WHERE bookId = :bookId")
    fun getSetList(bookId: String): Flow<List<SetEntity>>

    @Query("SELECT * FROM BookEntity")
    fun getBookList(): Flow<List<BookEntity>>
}
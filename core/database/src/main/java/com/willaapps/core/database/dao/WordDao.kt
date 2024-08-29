package com.willaapps.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.willaapps.core.database.entity.BookEntity
import com.willaapps.core.database.entity.SetEntity
import com.willaapps.core.database.entity.WordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Query("SELECT * FROM WordEntity " +
            "WHERE setId = :setId " +
            "AND groupNumber = :groupNumber")
    fun getSelectedWordGroup(
        setId: String,
        groupNumber: Int
    ): Flow<List<WordEntity>>

    @Query("SELECT * FROM SetEntity WHERE bookId = :bookId")
    fun getSetList(bookId: String): Flow<List<SetEntity>>

    @Query("SELECT * FROM BookEntity")
    fun getBookList(): Flow<List<BookEntity>>

    @Query("SELECT * FROM BookEntity WHERE id =:bookId")
    fun getBookById(bookId: String): Flow<BookEntity>

    @Query("SELECT * FROM SetEntity WHERE id=:setId")
    fun getSetById(setId: String): Flow<SetEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWords(words: List<WordEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSet(setEntity: SetEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBook(bookEntity: BookEntity)
}
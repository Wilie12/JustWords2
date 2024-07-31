package com.willaapps.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.willaapps.core.database.dao.WordDao
import com.willaapps.core.database.entity.BookEntity
import com.willaapps.core.database.entity.SetEntity
import com.willaapps.core.database.entity.WordEntity

@Database(
    entities = [
        WordEntity::class,
        SetEntity::class,
        BookEntity::class
    ],
    version = 1
)
abstract class WordDatabase: RoomDatabase() {

    abstract val wordDao: WordDao

    companion object {
        const val DATABASE_NAME = "word.db"
    }
}
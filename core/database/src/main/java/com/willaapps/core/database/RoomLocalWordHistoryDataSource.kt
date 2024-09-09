package com.willaapps.core.database

import android.database.sqlite.SQLiteFullException
import com.willaapps.core.database.dao.UserDao
import com.willaapps.core.database.mappers.toHistoryEntity
import com.willaapps.core.database.mappers.toWordHistory
import com.willaapps.core.domain.user.history.LocalWordHistoryDataSource
import com.willaapps.core.domain.user.history.WordHistory
import com.willaapps.core.domain.util.DataError
import com.willaapps.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalWordHistoryDataSource(
    private val userDao: UserDao
) : LocalWordHistoryDataSource {
    override suspend fun insertHistoryItem(wordHistory: WordHistory): Result<String, DataError.Local> {
        return try {
            val entity = wordHistory.toHistoryEntity()
            userDao.insertHistoryItem(entity)
            Result.Success(entity.id)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun insertHistoryItems(wordHistories: List<WordHistory>): Result<List<String>, DataError.Local> {
        return try {
            val entities = wordHistories.map { it.toHistoryEntity() }
            userDao.insertHistoryItems(entities)
            Result.Success(entities.map { it.id })
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override fun getHistoryItems(): Flow<List<WordHistory>> {
        return userDao.getHistoryItems().map { historyEntities ->
            historyEntities.map { it.toWordHistory() }
        }
    }

    override suspend fun deleteAllHistoryItems() {
        userDao.deleteAllHistoryItems()
    }
}
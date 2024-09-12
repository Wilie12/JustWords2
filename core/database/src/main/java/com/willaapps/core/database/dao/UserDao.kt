package com.willaapps.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.willaapps.core.database.entity.HistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Upsert
    suspend fun insertHistoryItem(historyEntity: HistoryEntity)
    @Upsert
    suspend fun insertHistoryItems(historyEntities: List<HistoryEntity>)
    @Query("SELECT * FROM Historyentity ORDER BY dateTimeUtc DESC")
    fun getHistoryItems(): Flow<List<HistoryEntity>>
    @Query("DELETE FROM HistoryEntity")
    suspend fun deleteAllHistoryItems()
}
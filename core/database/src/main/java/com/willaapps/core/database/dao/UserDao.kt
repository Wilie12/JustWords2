package com.willaapps.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.willaapps.core.database.entity.HistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    suspend fun insertHistoryItem(historyEntity: HistoryEntity)
    @Insert
    suspend fun insertHistoryItems(historyEntities: List<HistoryEntity>)
    @Query("SELECT * FROM Historyentity ORDER BY dateTimeUtc DESC")
    fun getHistoryItems(): Flow<List<HistoryEntity>>
    @Query("DELETE FROM HistoryEntity")
    suspend fun deleteAllHistoryItems()
}
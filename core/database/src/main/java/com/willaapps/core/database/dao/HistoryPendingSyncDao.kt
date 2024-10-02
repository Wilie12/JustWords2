package com.willaapps.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.willaapps.core.database.entity.HistoryPendingSyncEntity

@Dao
interface HistoryPendingSyncDao {
    @Query("SELECT * FROM HistoryPendingSyncEntity WHERE userId =:userId")
    suspend fun getAllHistoryPendingSyncEntities(userId: String): List<HistoryPendingSyncEntity>
    @Query("SELECT * FROM HistoryPendingSyncEntity WHERE userId =:userId")
    suspend fun getHistoryPendingSyncEntity(userId: String): HistoryPendingSyncEntity?
    @Upsert
    suspend fun upsertHistoryPendingSyncEntity(entity: HistoryPendingSyncEntity)
    @Query("DELETE FROM HistoryPendingSyncEntity WHERE userId = :userId")
    suspend fun deleteHistoryPendingSyncEntity(userId: String)
}
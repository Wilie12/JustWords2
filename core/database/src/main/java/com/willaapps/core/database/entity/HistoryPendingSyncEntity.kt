package com.willaapps.core.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryPendingSyncEntity(
    @Embedded val wordHistory: HistoryEntity,
    @PrimaryKey(autoGenerate = false)
    val wordHistoryId: String = wordHistory.id,
    val userId: String
)

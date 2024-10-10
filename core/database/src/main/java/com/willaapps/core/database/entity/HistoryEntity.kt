package com.willaapps.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.bson.types.ObjectId

@Entity(tableName = "HistoryEntity")
data class HistoryEntity(
    val bookName: String,
    val bookColor: Int,
    val setName: String,
    val groupNumber: Int,
    val dateTimeUtc: String,
    val perfectGuessed: Int,
    val wordListSize: Int,
    @PrimaryKey(autoGenerate = false)
    val id: String = ObjectId().toHexString()
)

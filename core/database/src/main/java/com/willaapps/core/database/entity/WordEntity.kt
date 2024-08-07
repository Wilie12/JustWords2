package com.willaapps.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.bson.types.ObjectId

@Entity
data class WordEntity(
    val sentence: String,
    val wordPl: String,
    val wordEng: String,
    val setId: String,
    val groupNumber: Int,
    @PrimaryKey(autoGenerate = false)
    val id: String = ObjectId().toHexString()
)

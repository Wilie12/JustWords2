package com.willaapps.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.bson.types.ObjectId

@Entity
data class BookEntity(
    val name: String,
    val color: Int,
    @PrimaryKey(autoGenerate = false)
    val id: String = ObjectId().toHexString()
)

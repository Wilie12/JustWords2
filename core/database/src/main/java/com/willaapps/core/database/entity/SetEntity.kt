package com.willaapps.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.bson.types.ObjectId

@Entity
data class SetEntity(
    val name: String,
    val bookId: String,
    @PrimaryKey(autoGenerate = false)
    val id: String = ObjectId().toHexString()
)

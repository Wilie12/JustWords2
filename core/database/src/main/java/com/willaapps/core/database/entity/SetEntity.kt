package com.willaapps.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.bson.types.ObjectId

@Entity(tableName = "SetEntity")
data class SetEntity(
    val name: String,
    val bookId: String,
    val numberOfGroups: Int,
    @PrimaryKey(autoGenerate = false)
    val id: String = ObjectId().toHexString()
)

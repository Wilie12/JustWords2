package com.willaapps.word.data

import kotlinx.serialization.Serializable

@Serializable
data class PreviousWordSerializable(
    val bookId: String,
    val bookColor: Int,
    val bookName: String,
    val setId: String,
    val setName: String,
    val groupNumber: Int
)

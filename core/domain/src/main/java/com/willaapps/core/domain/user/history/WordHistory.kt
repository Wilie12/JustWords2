package com.willaapps.core.domain.user.history

import java.time.ZonedDateTime

data class WordHistory(
    val bookName: String,
    val bookColor: Int,
    val setName: String,
    val groupNumber: Int,
    val dateTimeUtc: ZonedDateTime,
    val perfectGuessed: Int,
    val wordListSize: Int,
    val id: String?
)

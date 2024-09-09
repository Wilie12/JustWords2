package com.willaapps.core.database.mappers

import com.willaapps.core.database.entity.HistoryEntity
import com.willaapps.core.domain.user.history.WordHistory
import org.bson.types.ObjectId
import java.time.Instant
import java.time.ZoneId

fun HistoryEntity.toWordHistory(): WordHistory {
    return WordHistory(
        bookName = bookName,
        bookColor = bookColor,
        setName = setName,
        groupNumber = groupNumber,
        dateTimeUtc = Instant.parse(dateTimeUtc)
            .atZone(ZoneId.of("UTC")),
        perfectGuessed = perfectGuessed,
        wordListSize = wordListSize,
        id = id
    )
}

fun WordHistory.toHistoryEntity(): HistoryEntity {
    return HistoryEntity(
        bookName = bookName,
        bookColor = bookColor,
        setName = setName,
        groupNumber = groupNumber,
        dateTimeUtc = dateTimeUtc.toInstant().toString(),
        perfectGuessed = perfectGuessed,
        wordListSize = wordListSize,
        id = id ?: ObjectId().toHexString()
    )
}
package com.willaapps.core.data.user.history

import com.willaapps.core.domain.user.history.WordHistory
import java.time.Instant
import java.time.ZoneId

fun WordHistoryDto.toWordHistory(): WordHistory {
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

fun WordHistory.toWordHistoryDto(): WordHistoryDto {
    return WordHistoryDto(
        bookName = bookName,
        bookColor = bookColor,
        setName = setName,
        groupNumber = groupNumber,
        dateTimeUtc = dateTimeUtc.toInstant().toString(),
        perfectGuessed = perfectGuessed,
        wordListSize = wordListSize,
        id = id!!
    )
}
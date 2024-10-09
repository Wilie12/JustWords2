package com.willaapps.core.data.user

import com.willaapps.core.domain.user.UserInfo
import java.time.ZoneId
import java.time.ZonedDateTime

data class DateItem(
    val lastDayPlayed: Int,
    val nextDayAfterPlay: Int,
    val today: Int
)

fun UserInfo.getDateItem(): DateItem {
    return DateItem(
        lastDayPlayed = this.lastPlayedTimestamp
            .withZoneSameInstant(ZoneId.of("UTC")).dayOfMonth,
        nextDayAfterPlay = this.lastPlayedTimestamp
            .withZoneSameInstant(ZoneId.of("UTC")).plusDays(1).dayOfMonth,
        today = ZonedDateTime.now()
            .withZoneSameInstant(ZoneId.of("UTC")).dayOfMonth
    )
}

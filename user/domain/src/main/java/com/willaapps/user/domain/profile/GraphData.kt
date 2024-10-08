package com.willaapps.user.domain.profile

import java.time.ZoneId
import java.time.ZonedDateTime

data class GraphData(
    val todayPlays: List<DailyGraphItem> = emptyList(),
    val yesterdayPlays: List<DailyGraphItem> = emptyList(),
    val weeklyPlays: List<WeeklyGraphItem> = emptyList(),
    val today: Int = ZonedDateTime.now()
        .withZoneSameInstant(ZoneId.of("UTC")).dayOfWeek.value - 1,
)

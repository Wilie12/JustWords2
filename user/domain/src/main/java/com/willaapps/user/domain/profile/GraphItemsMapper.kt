package com.willaapps.user.domain.profile

import com.willaapps.core.domain.user.history.WordHistory
import java.time.ZoneId
import java.time.temporal.IsoFields

fun List<WordHistory>.filterHistoryItemsByDayAndYear(
    day: Int,
    year: Int
): List<WordHistory> {
    return this.filter {
        it.dateTimeUtc.withZoneSameInstant(ZoneId.of("UTC")).year == year &&
                it.dateTimeUtc.withZoneSameInstant(ZoneId.of("UTC")).dayOfMonth == day
    }
}

fun List<WordHistory>.mapHistoryItemsToDailyGraphItems(): List<DailyGraphItem> {
    return this.map { wordHistory ->
        DailyGraphItem(
            hour = wordHistory.dateTimeUtc.withZoneSameInstant(ZoneId.systemDefault()).hour,
            timesPlayed = 1
        )
    }
        .groupBy { dailyGraphItem -> dailyGraphItem.hour }
        .map { map ->
            DailyGraphItem(
                hour = map.key,
                timesPlayed = map.value.sumOf { it.timesPlayed }
            )
        }
}

fun List<WordHistory>.filterHistoryItemsByWeekYear(
    weekYear: Int
): List<WordHistory> {
    return this.filter {
        it.dateTimeUtc.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) == weekYear
    }
}

fun List<WordHistory>.mapHistoryItemsToWeeklyGraphItems(): List<WeeklyGraphItem> {
    return this.map { wordHistory ->
        WeeklyGraphItem(
            dayOfWeek = wordHistory.dateTimeUtc.withZoneSameInstant(ZoneId.systemDefault()).dayOfWeek.value - 1,
            timesPlayed = 1
        )
    }
        .groupBy { weeklyGraphItem -> weeklyGraphItem.dayOfWeek }
        .map { map ->
            WeeklyGraphItem(
                dayOfWeek = map.key,
                timesPlayed = map.value.sumOf { it.timesPlayed }
            )
        }
}
package com.willaapps.user.presentation.profile

import com.willaapps.core.domain.user.history.WordHistory
import com.willaapps.user.presentation.profile.util.DailyGraphItem
import com.willaapps.user.presentation.profile.util.ProfileLevel
import com.willaapps.user.presentation.profile.util.ProfileMode
import com.willaapps.user.presentation.profile.util.WeeklyGraphItem
import java.time.ZoneId
import java.time.ZonedDateTime

data class ProfileState(
    val username: String = "",
    val gamesCompleted: Int = 0,
    val dailyStreak: Int = 0,
    val bestStreak: Int = 0,
    val level: ProfileLevel = ProfileLevel.BEGINNER,
    val profileMode: ProfileMode = ProfileMode.HISTORY_MODE,
    val historyItems: List<WordHistory> = emptyList(),
    val todayPlays: List<DailyGraphItem> = emptyList(),
    val yesterdayPlays: List<DailyGraphItem> = emptyList(),
    val weeklyPlays: List<WeeklyGraphItem> = emptyList(),
    val today: Int = ZonedDateTime.now()
        .withZoneSameInstant(ZoneId.of("UTC")).dayOfWeek.value - 1,
    val isLoading: Boolean = false
)
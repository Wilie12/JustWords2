package com.willaapps.user.presentation.profile

import com.willaapps.core.domain.user.history.WordHistory
import com.willaapps.user.presentation.profile.util.ProfileLevel
import com.willaapps.user.presentation.profile.util.ProfileMode

data class ProfileState(
    val username: String = "",
    val gamesCompleted: Int = 0,
    val dailyStreak: Int = 0,
    val bestStreak: Int = 0,
    val level: ProfileLevel = ProfileLevel.BEGINNER,
    val profileMode: ProfileMode = ProfileMode.HISTORY_MODE,
    val historyItems: List<WordHistory> = emptyList(),
    val isLoading: Boolean = false
)
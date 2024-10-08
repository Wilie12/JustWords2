package com.willaapps.user.presentation.profile

import com.willaapps.core.domain.user.history.WordHistory
import com.willaapps.user.domain.profile.GraphData
import com.willaapps.user.domain.profile.ProfileMode
import com.willaapps.user.presentation.profile.util.calculateLevel

data class ProfileState(
    val username: String = "",
    val dailyStreak: Int = 0,
    val bestStreak: Int = 0,
    val profileMode: ProfileMode = ProfileMode.HISTORY_MODE,
    val historyItems: List<WordHistory> = emptyList(),
    val graphData: GraphData = GraphData(),
    val showDialog: Boolean = false,
    val isLoading: Boolean = false
) {
    val gamesCompleted = historyItems.size
    val level = calculateLevel(historyItems)
}
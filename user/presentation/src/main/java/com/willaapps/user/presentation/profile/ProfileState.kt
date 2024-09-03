package com.willaapps.user.presentation.profile

data class ProfileState(
    val username: String = "",
    val gamesCompleted: Int = 0,
    val dailyStreak: Int = 0,
    val bestStreak: Int = 0,
    val level: String = "",
    val profileMode: ProfileMode = ProfileMode.HISTORY_MODE
)
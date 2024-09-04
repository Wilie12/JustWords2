package com.willaapps.core.domain.user

import java.time.ZonedDateTime

data class UserInfo(
    val dailyStreak: Int,
    val bestStreak: Int,
    val currentGoal: Int,
    val dailyGoal: Int,
    val lastPlayedTimestamp: ZonedDateTime,
    val userName: String,
    val userId: String
)
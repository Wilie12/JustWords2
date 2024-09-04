package com.willaapps.core.domain.user

import java.time.ZonedDateTime

data class UserInfo(
    val dailyStreak: Int,
    val bestStreak: Int,
    val currentGoal: Int,
    val dailyGoal: Int,
    val lastPlayedTimestamp: ZonedDateTime,
    val username: String,
    val userId: String
)
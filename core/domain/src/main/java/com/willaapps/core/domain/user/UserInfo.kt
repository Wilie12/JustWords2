package com.willaapps.core.domain.user

import java.time.ZoneId
import java.time.ZonedDateTime

data class UserInfo(
    val dailyStreak: Int,
    val bestStreak: Int,
    val currentGoal: Int,
    val dailyGoal: Int,
    val lastPlayedTimestamp: ZonedDateTime,
    val lastEditedTimestamp: ZonedDateTime,
    val username: String,
    val userId: String
) {
    fun getUpdatedUserInfo(
        username: String = this.username,
        dailyGoal: Int = this.dailyGoal
    ): UserInfo {
        return UserInfo(
            dailyStreak = this.dailyStreak,
            bestStreak = this.bestStreak,
            currentGoal = this.currentGoal,
            dailyGoal = dailyGoal,
            lastPlayedTimestamp = this.lastPlayedTimestamp,
            lastEditedTimestamp = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")),
            username = username,
            userId = this.userId
        )
    }
}
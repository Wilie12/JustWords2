package com.willaapps.core.data.user

import com.willaapps.core.domain.user.UserInfo
import java.time.Instant
import java.time.ZoneId

fun UserInfo.toUserInfoSerializable(): UserInfoSerializable {
    return UserInfoSerializable(
        dailyStreak = dailyStreak,
        bestStreak = bestStreak,
        dailyGoal = dailyGoal,
        lastPlayedTimestamp = lastPlayedTimestamp.toInstant().toString(),
        currentGoal = currentGoal,
        username = username,
        userId = userId
    )
}

fun UserInfoSerializable.toUserInfo(): UserInfo {
    return UserInfo(
        dailyStreak = dailyStreak,
        bestStreak = bestStreak,
        dailyGoal = dailyGoal,
        lastPlayedTimestamp = Instant.parse(lastPlayedTimestamp)
            .atZone(ZoneId.of("UTC")),
        currentGoal = currentGoal,
        username = username,
        userId = userId
    )
}
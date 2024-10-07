package com.willaapps.core.data.user

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoSerializable(
    val dailyStreak: Int,
    val bestStreak: Int,
    val currentGoal: Int,
    val dailyGoal: Int,
    val lastPlayedTimestamp: String,
    val lastEditedTimestamp: String,
    val username: String,
    val userId: String
)

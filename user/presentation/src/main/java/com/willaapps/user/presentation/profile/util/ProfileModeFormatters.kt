package com.willaapps.user.presentation.profile.util

fun profileModeToString(profileMode: ProfileMode): String {
    return when (profileMode) {
        ProfileMode.HISTORY_MODE -> "History"
        ProfileMode.STATS_MODE -> "Stats"
    }
}
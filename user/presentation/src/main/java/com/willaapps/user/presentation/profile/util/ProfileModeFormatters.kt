package com.willaapps.user.presentation.profile.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.willaapps.user.presentation.R

@Composable
fun profileModeToString(profileMode: ProfileMode): String {
    return when (profileMode) {
        ProfileMode.HISTORY_MODE -> stringResource(R.string.history)
        ProfileMode.STATS_MODE -> stringResource(R.string.stats)
    }
}
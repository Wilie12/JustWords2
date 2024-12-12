package com.willaapps.user.presentation.profile.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.willaapps.core.domain.user.history.WordHistory
import com.willaapps.user.domain.profile.ProfileLevel
import com.willaapps.user.presentation.R

@Composable
fun profileLevelToString(profileLevel: ProfileLevel): String {
    return when (profileLevel) {
        ProfileLevel.BEGINNER -> stringResource(R.string.beginner)
        ProfileLevel.INTERMEDIATE -> stringResource(R.string.intermediate)
        ProfileLevel.ADVANCED -> stringResource(R.string.advanced)
        ProfileLevel.UNKNOWN -> stringResource(R.string.unknown)
    }
}

fun calculateLevel(historyItems: List<WordHistory>): ProfileLevel {

    if (historyItems.isEmpty()) {
        return ProfileLevel.UNKNOWN
    }

    val listOfAveragePerfects = mutableListOf<Float>()

    historyItems.forEach { historyItem ->
        val perfectPercentage =
            historyItem.perfectGuessed.toFloat() / historyItem.wordListSize.toFloat()
        listOfAveragePerfects.add(perfectPercentage)
    }

    val avgPerfectGuesses = listOfAveragePerfects.average()

    return when (avgPerfectGuesses) {
        in 0.0.. 0.5 -> ProfileLevel.BEGINNER
        in 0.8..1.0 -> ProfileLevel.ADVANCED
        else -> ProfileLevel.INTERMEDIATE
    }
}
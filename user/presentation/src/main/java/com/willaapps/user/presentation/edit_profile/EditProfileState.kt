@file:OptIn(ExperimentalFoundationApi::class)

package com.willaapps.user.presentation.edit_profile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState

data class EditProfileState(
    val username: TextFieldState = TextFieldState(),
    val isValidUsername: Boolean = true,
    val email: TextFieldState = TextFieldState(),
    val dailyGoal: TextFieldState = TextFieldState(),
    val isValidDailyGoal: Boolean = true,
    val isLoading: Boolean = false,
) {
    val canSave = isValidUsername && isValidDailyGoal && !isLoading
}

@file:OptIn(ExperimentalFoundationApi::class)

package com.willaapps.auth.presentation.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import com.willaapps.auth.domain.PasswordValidationState

data class RegisterState(
    val username: TextFieldState = TextFieldState(),
    val isValidUsername: Boolean = false,
    val email: TextFieldState = TextFieldState(),
    val isValidEmail: Boolean = false,
    val password: TextFieldState = TextFieldState(),
    val isPasswordVisible: Boolean = false,
    val passwordValidationState: PasswordValidationState = PasswordValidationState(),
    val canRegister: Boolean = false,
    val isRegistering: Boolean = false
)
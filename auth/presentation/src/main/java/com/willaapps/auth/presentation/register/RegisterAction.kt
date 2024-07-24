package com.willaapps.auth.presentation.register

sealed interface RegisterAction {
    data object OnSignUpClick: RegisterAction
    data object OnSignInClick: RegisterAction
    data object OnBackClick: RegisterAction
    data object OnTogglePasswordVisibilityClick: RegisterAction
}
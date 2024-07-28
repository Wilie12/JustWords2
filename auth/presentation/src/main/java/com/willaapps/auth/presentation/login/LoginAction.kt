package com.willaapps.auth.presentation.login

sealed interface LoginAction {
    data object OnBackClick: LoginAction
    data object OnSignInClick: LoginAction
    data object OnSignUpClick: LoginAction
    data object OnTogglePasswordVisibilityClick: LoginAction
}
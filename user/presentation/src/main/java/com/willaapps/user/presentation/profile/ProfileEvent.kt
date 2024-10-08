package com.willaapps.user.presentation.profile

sealed interface ProfileEvent {
    data object LogoutSuccessfully: ProfileEvent
}
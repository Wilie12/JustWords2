package com.willaapps.user.presentation.profile

sealed interface ProfileAction {
    data object OnBackClick: ProfileAction
    data object OnLogoutClick: ProfileAction
    data object OnEditProfileClick: ProfileAction
    data class OnModeChangeClick(val profileMode: ProfileMode): ProfileAction
}
package com.willaapps.user.presentation.profile

import com.willaapps.user.domain.profile.ProfileMode

sealed interface ProfileAction {
    data object OnBackClick: ProfileAction
    data object OnLogoutClick: ProfileAction
    data object OnEditProfileClick: ProfileAction
    data class OnModeChangeClick(val profileMode: ProfileMode): ProfileAction
}
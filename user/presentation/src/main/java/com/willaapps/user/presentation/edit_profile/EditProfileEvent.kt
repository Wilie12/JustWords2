package com.willaapps.user.presentation.edit_profile

import com.willaapps.core.presentation.ui.UiText

sealed interface EditProfileEvent {
    data object EditedSuccessfully: EditProfileEvent
    data class Error(val error: UiText): EditProfileEvent
}
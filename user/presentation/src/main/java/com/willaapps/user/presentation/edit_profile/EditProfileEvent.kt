package com.willaapps.user.presentation.edit_profile

sealed interface EditProfileEvent {
    data object EditedSuccessfully: EditProfileEvent
}
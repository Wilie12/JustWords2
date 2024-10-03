package com.willaapps.user.presentation.edit_profile

sealed interface EditProfileAction {
    data object OnSaveClick: EditProfileAction
    data object OnCancelClick: EditProfileAction
    data object OnBackClick: EditProfileAction
}
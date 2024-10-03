@file:Suppress("OPT_IN_USAGE_FUTURE_ERROR")

package com.willaapps.user.presentation.edit_profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class EditProfileViewModel: ViewModel() {

    var state by mutableStateOf(EditProfileState())
        private set

    fun onAction(action: EditProfileAction) {

    }
}
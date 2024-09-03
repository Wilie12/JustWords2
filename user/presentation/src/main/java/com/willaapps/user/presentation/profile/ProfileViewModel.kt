package com.willaapps.user.presentation.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ProfileViewModel: ViewModel() {

    var state by mutableStateOf(ProfileState())
        private set
    // TODO - read user from datastore
    // TODO - read user data from database
    fun onAction(action: ProfileAction) {

    }
}
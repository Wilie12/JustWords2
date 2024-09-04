package com.willaapps.user.presentation.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.willaapps.core.domain.user.UserStorage
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProfileViewModel(
    private val userStorage: UserStorage
): ViewModel() {

    var state by mutableStateOf(ProfileState())
        private set
    // TODO - read user data from database
    init {
        userStorage.get()
            .filterNotNull()
            .onEach {
                state = state.copy(
                    username = it.username,
                    dailyStreak = it.dailyStreak,
                    bestStreak = it.bestStreak
                )
            }
            .launchIn(viewModelScope)
    }
    fun onAction(action: ProfileAction) {
        when (action) {
            ProfileAction.OnLogoutClick -> Unit // TODO
            is ProfileAction.OnModeChangeClick -> {
                state = state.copy(
                    profileMode = action.profileMode
                )
            }
            else -> Unit
        }
    }
}
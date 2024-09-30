package com.willaapps.user.presentation.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.willaapps.core.domain.user.UserStorage
import com.willaapps.core.domain.user.history.WordHistoryRepository
import com.willaapps.user.presentation.profile.util.calculateLevel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userStorage: UserStorage,
    private val wordHistoryRepository: WordHistoryRepository
) : ViewModel() {

    var state by mutableStateOf(ProfileState())
        private set
    // TODO - map historyItems to graph items

    init {
        state = state.copy(isLoading = true)
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
        wordHistoryRepository.getHistoryItems()
            .onEach {
                state = state.copy(
                    historyItems = it,
                    level = calculateLevel(historyItems = it),
                    isLoading = false
                )
            }
            .launchIn(viewModelScope)
        viewModelScope.launch {
            wordHistoryRepository.fetchHistoryItems()
        }
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
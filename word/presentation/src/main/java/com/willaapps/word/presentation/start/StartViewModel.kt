package com.willaapps.word.presentation.start

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.willaapps.core.domain.auth.SessionStorage
import com.willaapps.core.domain.user.UserInfoRepository
import com.willaapps.core.domain.user.UserStorage
import com.willaapps.core.domain.word.LocalWordDataSource
import com.willaapps.word.domain.PreviousWordStorage
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class StartViewModel(
    localWordDataSource: LocalWordDataSource,
    previousWordStorage: PreviousWordStorage,
    private val userInfoRepository: UserInfoRepository,
    userStorage: UserStorage,
    private val sessionStorage: SessionStorage
) : ViewModel() {

    // TODO - check userInfo fetching and syncing
    var state by mutableStateOf(StartState())
        private set

    init {
        state = state.copy(isLoading = true)

        viewModelScope.launch {
            userInfoRepository.getUserInfo(
                userId = sessionStorage.get()?.userId ?: ""
            )
        }
        userStorage.get()
            .filterNotNull()
            .onEach {
                state = state.copy(
                    userName = it.username,
                    dailyGoalAim = it.dailyGoal,
                    dailyGoalCurrent = it.currentGoal
                )
                state = state.copy(isLoading = false)

            }
            .launchIn(viewModelScope)
        localWordDataSource.getBooks()
            .onEach {
                state = state.copy(
                    books = it
                )
            }
            .launchIn(viewModelScope)
        previousWordStorage.get()
            .onEach {
                state = state.copy(
                    previousWord = it
                )
            }
            .launchIn(viewModelScope)
    }
}
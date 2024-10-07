package com.willaapps.user.presentation.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.willaapps.core.domain.user.UserStorage
import com.willaapps.core.domain.user.history.WordHistoryRepository
import com.willaapps.user.domain.profile.filterHistoryItemsByDayAndYear
import com.willaapps.user.domain.profile.filterHistoryItemsByWeekYear
import com.willaapps.user.domain.profile.mapHistoryItemsToDailyGraphItems
import com.willaapps.user.domain.profile.mapHistoryItemsToWeeklyGraphItems
import com.willaapps.user.presentation.profile.util.calculateLevel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.IsoFields

class ProfileViewModel(
    userStorage: UserStorage,
    private val wordHistoryRepository: WordHistoryRepository
) : ViewModel() {
    // TODO - logout
    var state by mutableStateOf(ProfileState())
        private set

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
                    gamesCompleted = it.size,
                    historyItems = it,
                    level = calculateLevel(historyItems = it),
                    isLoading = false,
                    todayPlays = it.filterHistoryItemsByDayAndYear(
                        day = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).dayOfMonth,
                        year = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).year
                    )
                        .mapHistoryItemsToDailyGraphItems(),
                    yesterdayPlays = it.filterHistoryItemsByDayAndYear(
                        day = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC"))
                            .minusDays(1).dayOfMonth,
                        year = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).year
                    )
                        .mapHistoryItemsToDailyGraphItems(),
                    weeklyPlays = it.filterHistoryItemsByWeekYear(
                        weekYear = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC"))
                            .get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)
                    )
                        .mapHistoryItemsToWeeklyGraphItems()
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
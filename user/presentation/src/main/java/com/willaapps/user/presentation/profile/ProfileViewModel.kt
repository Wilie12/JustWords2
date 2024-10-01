package com.willaapps.user.presentation.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.willaapps.core.domain.user.UserStorage
import com.willaapps.core.domain.user.history.WordHistory
import com.willaapps.core.domain.user.history.WordHistoryRepository
import com.willaapps.user.presentation.profile.util.DailyGraphItem
import com.willaapps.user.presentation.profile.util.calculateLevel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.ZonedDateTime

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
                    gamesCompleted = it.size,
                    historyItems = it,
                    level = calculateLevel(historyItems = it),
                    isLoading = false,
                    todayPlays = filterHistoryItemsByDayAndYear(
                        historyItems = it,
                        day = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).dayOfMonth,
                        year = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).year
                    )
                        .mapHistoryItemsToDailyGraphItems(),
                    yesterdayPlays = filterHistoryItemsByDayAndYear(
                        historyItems = it,
                        day = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).minusDays(1).dayOfMonth,
                        year = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).year
                    )
                        .mapHistoryItemsToDailyGraphItems()
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

    private fun filterHistoryItemsByDayAndYear(
        historyItems: List<WordHistory>,
        day: Int,
        year: Int
    ): List<WordHistory> {
        return historyItems.filter {
            it.dateTimeUtc.withZoneSameInstant(ZoneId.of("UTC")).year == year &&
                    it.dateTimeUtc.withZoneSameInstant(ZoneId.of("UTC")).dayOfMonth == day
        }
    }

    private fun List<WordHistory>.mapHistoryItemsToDailyGraphItems(): List<DailyGraphItem> {
        return this.map { wordHistory ->
            DailyGraphItem(
                hour = wordHistory.dateTimeUtc.withZoneSameInstant(ZoneId.systemDefault()).hour,
                timesPlayed = 1
            )
        }
            .groupBy { dailyGraphItem -> dailyGraphItem.hour }
            .map { map ->
                DailyGraphItem(
                    hour = map.key,
                    timesPlayed = map.value.sumOf { it.timesPlayed }
                )
            }
    }
}
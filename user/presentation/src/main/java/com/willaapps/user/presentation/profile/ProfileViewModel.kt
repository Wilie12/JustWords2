package com.willaapps.user.presentation.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.willaapps.core.domain.auth.SessionStorage
import com.willaapps.core.domain.user.UserStorage
import com.willaapps.core.domain.user.history.SyncHistoryScheduler
import com.willaapps.core.domain.user.history.WordHistoryRepository
import com.willaapps.core.domain.word.LocalWordDataSource
import com.willaapps.user.domain.profile.filterHistoryItemsByDayAndYear
import com.willaapps.user.domain.profile.filterHistoryItemsByWeekYear
import com.willaapps.user.domain.profile.mapHistoryItemsToDailyGraphItems
import com.willaapps.user.domain.profile.mapHistoryItemsToWeeklyGraphItems
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.IsoFields

class ProfileViewModel(
    private val userStorage: UserStorage,
    private val wordHistoryRepository: WordHistoryRepository,
    private val applicationScope: CoroutineScope,
    private val syncHistoryScheduler: SyncHistoryScheduler,
    private val sessionStorage: SessionStorage,
    private val localWordDataSource: LocalWordDataSource
) : ViewModel() {
    // TODO - logout
    var state by mutableStateOf(ProfileState())
        private set

    private val _channel = Channel<ProfileEvent>()
    val eventChannel = _channel.receiveAsFlow()

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
                    isLoading = false,
                    graphData = state.graphData.copy(
                        todayPlays = it.filterHistoryItemsByDayAndYear(
                            day = ZonedDateTime.now()
                                .withZoneSameInstant(ZoneId.of("UTC")).dayOfMonth,
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
                )
            }
            .launchIn(viewModelScope)
        viewModelScope.launch {
            wordHistoryRepository.fetchHistoryItems()
        }
    }

    fun onAction(action: ProfileAction) {
        when (action) {
            ProfileAction.OnLogoutClick -> {
                state = state.copy(showDialog = true)
            }

            is ProfileAction.OnModeChangeClick -> {
                state = state.copy(
                    profileMode = action.profileMode
                )
            }

            ProfileAction.OnLogoutDismiss -> {
                state = state.copy(showDialog = false)
            }

            ProfileAction.OnLogoutConfirm -> {
                applicationScope.launch {
                    state = state.copy(showDialog = false)
                    logout()
                    _channel.send(ProfileEvent.LogoutSuccessfully)
                }
            }

            else -> Unit
        }
    }

    private fun logout() {
        applicationScope.launch {
            userStorage.clearUserInfo()
            syncHistoryScheduler.cancelAllSyncs()
            wordHistoryRepository.deleteAllHistoryItems()
            wordHistoryRepository.logout()
            sessionStorage.set(null)
            localWordDataSource.deleteAll()
        }
    }
}
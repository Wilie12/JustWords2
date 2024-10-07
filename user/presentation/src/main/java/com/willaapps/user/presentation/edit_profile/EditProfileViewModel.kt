@file:Suppress("OPT_IN_USAGE_FUTURE_ERROR")

package com.willaapps.user.presentation.edit_profile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.willaapps.core.domain.user.UserStorage
import com.willaapps.user.domain.EditProfileValidator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
class EditProfileViewModel(
    private val userStorage: UserStorage
): ViewModel() {

    var state by mutableStateOf(EditProfileState())
        private set

    private val _channel = Channel<EditProfileEvent>()
    val eventChannel = _channel.receiveAsFlow()

    init {
        state = state.copy(isLoading = true)
        userStorage.get()
            .filterNotNull()
            .onEach {
                state.username.edit {
                    append(it.username)
                }
                state.dailyGoal.edit {
                    append(it.dailyGoal.toString())
                }
                state = state.copy(
                    currentUserInfo = it,
                    isLoading = false
                )
            }
            .launchIn(viewModelScope)
        state.username
            .textAsFlow()
            .onEach { username ->
                val isValidUserName = EditProfileValidator.validateUsername(username.toString())
                state = state.copy(
                    isValidUsername = isValidUserName
                )
            }
            .launchIn(viewModelScope)
        state.dailyGoal
            .textAsFlow()
            .onEach { dailyGoal ->
                val isValidDailyGoal = EditProfileValidator.validateDailyGoal(dailyGoal.toString())
                state = state.copy(
                    isValidDailyGoal = isValidDailyGoal
                )
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: EditProfileAction) {
        when (action) {
            EditProfileAction.OnSaveClick -> {
                state.currentUserInfo?.let {
                    viewModelScope.launch {
                        userStorage.setUserInfo(
                            userInfo = it.getUpdatedUserInfo(
                                username = state.username.text.toString(),
                                dailyGoal = state.dailyGoal.text.toString().toInt()
                            )
                        )
                        _channel.send(EditProfileEvent.EditedSuccessfully)
                    }
                }
            }
            else -> Unit
        }
    }
}
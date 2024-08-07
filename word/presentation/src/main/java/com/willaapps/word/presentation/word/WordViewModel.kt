@file:Suppress("OPT_IN_USAGE_FUTURE_ERROR")
@file:OptIn(ExperimentalFoundationApi::class)

package com.willaapps.word.presentation.word

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.clearText
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.willaapps.core.domain.word.LocalWordRepository
import com.willaapps.word.domain.toWordGuessable
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class WordViewModel(
    private val localWordRepository: LocalWordRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(WordState())
        private set

    init {
        state = state.copy(
            bookColor = checkNotNull(savedStateHandle["bookColor"])
        )
        localWordRepository.getSelectedWordGroup(
            bookId = checkNotNull(savedStateHandle["bookId"]),
            setId = checkNotNull(savedStateHandle["setId"]),
            groupNumber = checkNotNull(savedStateHandle["groupNumber"]),
        )
            .onEach { words ->
                state = state.copy(
                    words = words.map { word ->
                        word.toWordGuessable()
                    },
                    wordsNumber = words.size
                )
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: WordAction) {
        when (action) {
            is WordAction.OnButtonCLick -> {
                when (action.buttonOption) {
                    ButtonOption.BUTTON_CHECK -> {
                        if (state.words[0].isFirstTimeSeen) {
                            state.words[0].isFirstTimeSeen = false
                            if (state.isCorrect) {
                                state = state.copy(
                                    perfectGuesses = state.perfectGuesses + 1
                                )
                            }
                        }
                        state = state.copy(
                            buttonOption = ButtonOption.BUTTON_NEXT
                        )
                    }
                    ButtonOption.BUTTON_NEXT -> {
                        if (state.isCorrect) {
                            val newList = state.words.toMutableList()
                            newList.removeAt(0)

                            state = state.copy(
                                words = newList
                            )
                        }
                        if (state.words.isNotEmpty()) {
                            state = state.copy(
                                words = state.words.shuffled(),
                                buttonOption = ButtonOption.BUTTON_CHECK
                            )
                        } else {
                            // TODO - save to history
                            // TODO - save in daily goal
                            state = state.copy(
                                buttonOption = ButtonOption.BUTTON_FINISH
                            )
                        }

                        state.answer.clearText()
                    }
                    ButtonOption.BUTTON_FINISH -> TODO()
                }
            }
            else -> Unit
        }
    }
}
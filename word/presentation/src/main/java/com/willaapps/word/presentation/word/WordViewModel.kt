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
import com.willaapps.word.domain.PreviousWord
import com.willaapps.word.domain.PreviousWordStorage
import com.willaapps.word.domain.toWordGuessable
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class WordViewModel(
    private val localWordRepository: LocalWordRepository,
    private val savedStateHandle: SavedStateHandle,
    private val previousWordStorage: PreviousWordStorage
) : ViewModel() {

    var state by mutableStateOf(WordState())
        private set

    init {
        localWordRepository
            .getLocalBookById(checkNotNull(savedStateHandle["bookId"]))
            .onEach { book ->
                state = state.copy(
                    book = book
                )
            }
            .launchIn(viewModelScope)
        localWordRepository
            .getLocalSetById(checkNotNull(savedStateHandle["setId"]))
            .onEach { wordSet ->
                state = state.copy(
                    set = wordSet
                )
            }
            .launchIn(viewModelScope)
        localWordRepository.getSelectedWordGroup(
            setId = checkNotNull(savedStateHandle["setId"]),
            groupNumber = checkNotNull(savedStateHandle["groupNumber"]),
        )
            .onEach { words ->
                state = state.copy(
                    words = words
                        .shuffled()
                        .map { word ->
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
                            viewModelScope.launch {
                                previousWordStorage.set(
                                    PreviousWord(
                                        bookId = state.book.bookId,
                                        bookColor = state.book.color,
                                        bookName = state.book.name,
                                        setId = state.set.id,
                                        setName = state.set.name,
                                        groupNumber = checkNotNull(savedStateHandle["groupNumber"])
                                    )
                                )
                            }
                            state = state.copy(
                                buttonOption = ButtonOption.BUTTON_FINISH
                            )
                        }
                        state.answer.clearText()
                    }

                    else -> Unit
                }
            }

            else -> Unit
        }
    }
}
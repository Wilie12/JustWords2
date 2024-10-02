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
import com.willaapps.core.domain.user.UserStorage
import com.willaapps.core.domain.user.history.WordHistory
import com.willaapps.core.domain.user.history.WordHistoryRepository
import com.willaapps.core.domain.util.Result
import com.willaapps.core.domain.word.LocalWordDataSource
import com.willaapps.word.domain.PreviousWord
import com.willaapps.word.domain.PreviousWordStorage
import com.willaapps.word.domain.toWordGuessable
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.ZonedDateTime

class WordViewModel(
    private val localWordDataSource: LocalWordDataSource,
    private val savedStateHandle: SavedStateHandle,
    private val previousWordStorage: PreviousWordStorage,
    private val userStorage: UserStorage,
    private val wordHistoryRepository: WordHistoryRepository
) : ViewModel() {

    var state by mutableStateOf(WordState())
        private set

    init {
        localWordDataSource
            .getBookById(checkNotNull(savedStateHandle["bookId"]))
            .onEach { book ->
                state = state.copy(
                    book = book
                )
            }
            .launchIn(viewModelScope)
        localWordDataSource
            .getSetById(checkNotNull(savedStateHandle["setId"]))
            .onEach { wordSet ->
                state = state.copy(
                    set = wordSet
                )
            }
            .launchIn(viewModelScope)
        localWordDataSource.getSelectedWordGroup(
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
                            viewModelScope.launch {
                                userStorage.setIncreasedDailyGoal()
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
                                when (val result = wordHistoryRepository.insertHistoryItem(
                                    wordHistory = WordHistory(
                                        bookName = state.book.name,
                                        bookColor = state.book.color,
                                        setName = state.set.name,
                                        groupNumber = checkNotNull(savedStateHandle["groupNumber"]),
                                        dateTimeUtc = ZonedDateTime.now()
                                            .withZoneSameInstant(ZoneId.of("UTC")),
                                        perfectGuessed = state.perfectGuesses,
                                        wordListSize = state.wordsNumber,
                                        id = null
                                    )
                                )) {
                                    is Result.Error -> {
                                        // TODO - send error event
                                        // tODO - sync history later with worker
                                    }
                                    is Result.Success -> Unit
                                }
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
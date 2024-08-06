@file:OptIn(ExperimentalFoundationApi::class)

package com.willaapps.word.presentation.word

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import com.willaapps.word.domain.WordGuessable

data class WordState(
    val words: List<WordGuessable> = emptyList(),
    val bookColor: Int = 0,
    val sentence: String = "",
    val wordPl: String = "",
    val wordEng: String = "",
    val isCorrect: Boolean = false,
    val answer: TextFieldState = TextFieldState(),
    val buttonOption: ButtonOption = ButtonOption.BUTTON_CHECK
)

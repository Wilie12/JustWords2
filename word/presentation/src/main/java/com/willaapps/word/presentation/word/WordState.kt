@file:OptIn(ExperimentalFoundationApi::class)

package com.willaapps.word.presentation.word

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import com.willaapps.word.domain.WordGuessable

data class WordState(
    val words: List<WordGuessable> = emptyList(),
    val wordsNumber: Int = 0,
    val bookColor: Int = 0,
    val answer: TextFieldState = TextFieldState(),
    val buttonOption: ButtonOption = ButtonOption.BUTTON_CHECK,
    val perfectGuesses: Int = 0
) {
    val isLoading: Boolean
        get() = words.isEmpty() && buttonOption == ButtonOption.BUTTON_CHECK
    val sentence: String
        get() = words[0].sentence
    val wordPl: String
        get() = words[0].wordPl
    val wordEng: String
        get() = words[0].wordEng
    val isCorrect: Boolean
        get() = answer.text.toString() == wordEng
}

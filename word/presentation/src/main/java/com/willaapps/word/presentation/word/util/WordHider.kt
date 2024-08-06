package com.willaapps.word.presentation.word.util

import com.willaapps.word.presentation.word.ButtonOption

fun hideWord(
    sentence: String,
    wordEng: String,
    buttonOption: ButtonOption
): String {
    var hiddenWord = ""
    wordEng.forEach { character ->
        hiddenWord += if (character == ' ') {
            " "
        } else {
            "_"
        }
    }

    return when (buttonOption) {
        ButtonOption.BUTTON_CHECK -> sentence.replace(wordEng, hiddenWord)
        ButtonOption.BUTTON_NEXT -> sentence
        ButtonOption.BUTTON_FINISH -> sentence
    }
}
package com.willaapps.word.presentation.word

sealed interface WordAction {
    data object OnBackClick: WordAction
    data class OnButtonCLick(val buttonOption: ButtonOption): WordAction
}
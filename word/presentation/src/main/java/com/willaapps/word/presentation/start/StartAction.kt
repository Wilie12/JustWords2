package com.willaapps.word.presentation.start

sealed interface StartAction {

    data object OnUserClick: StartAction
    data object OnShopClick: StartAction
    data class OnBookClick(val bookId: String): StartAction
}
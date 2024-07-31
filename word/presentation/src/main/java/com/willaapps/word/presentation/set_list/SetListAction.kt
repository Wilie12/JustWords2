package com.willaapps.word.presentation.set_list

sealed interface SetListAction {
    data object OnBackClick: SetListAction
    data class OnGroupClick(
        val selectedSetId: String,
        val selectedGroup: Int
    ): SetListAction
}
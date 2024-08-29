package com.willaapps.word.presentation.set_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.willaapps.core.domain.word.LocalWordDataSource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SetListViewModel(
    private val localWordDataSource: LocalWordDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(SetListState())
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
            .getWordSetsById(checkNotNull(savedStateHandle["bookId"]))
            .onEach { sets ->
                state = state.copy(
                    sets = sets
                )
            }
            .launchIn(viewModelScope)
    }
}
package com.willaapps.word.presentation.set_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.willaapps.core.domain.word.LocalWordRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SetListViewModel(
    private val localWordRepository: LocalWordRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(SetListState())
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
            .getLocalWordSets(checkNotNull(savedStateHandle["bookId"]))
            .onEach { sets ->
                state = state.copy(
                    sets = sets
                )
            }
            .launchIn(viewModelScope)
    }
}
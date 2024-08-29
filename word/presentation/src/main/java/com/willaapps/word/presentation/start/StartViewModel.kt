package com.willaapps.word.presentation.start

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.willaapps.core.domain.word.LocalWordDataSource
import com.willaapps.word.domain.PreviousWordStorage
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class StartViewModel(
    private val localWordDataSource: LocalWordDataSource,
    private val previousWordStorage: PreviousWordStorage
) : ViewModel() {

    var state by mutableStateOf(StartState())
        private set

    init {
        localWordDataSource.getBooks()
            .onEach {
                state = state.copy(
                    books = it
                )
            }.launchIn(viewModelScope)
        previousWordStorage.get()
            .onEach {
                state = state.copy(
                    previousWord = it
                )
            }
            .launchIn(viewModelScope)
    }
}
package com.willaapps.word.presentation.start

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.willaapps.core.domain.word.LocalWordRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class StartViewModel(
    private val localWordRepository: LocalWordRepository
) : ViewModel() {

    var state by mutableStateOf(StartState())
        private set

    init {
        localWordRepository.getLocalBooks()
            .onEach {
                state = state.copy(
                    books = it
                )
            }.launchIn(viewModelScope)
    }

    fun onAction(action: StartAction) {

    }
}
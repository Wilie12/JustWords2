package com.willaapps.word.presentation.start

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class StartViewModel: ViewModel() {

    var state by mutableStateOf(StartState())
        private set

    fun onAction(action: StartAction) {

    }
}
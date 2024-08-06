@file:Suppress("OPT_IN_USAGE_FUTURE_ERROR")

package com.willaapps.word.presentation.word

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class WordViewModel: ViewModel() {

    var state by mutableStateOf(WordState())
        private set

    fun onAction(action: WordAction) {

    }
}
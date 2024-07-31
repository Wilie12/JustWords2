package com.willaapps.word.presentation.set_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SetListViewModel: ViewModel() {

    var state by mutableStateOf(SetListState())
        private set

    fun onAction(action: SetListAction) {

    }
}
package com.willaapps.shop.presentation.shop

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.willaapps.core.domain.util.Result
import com.willaapps.core.presentation.ui.asUiText
import com.willaapps.shop.domain.ShopRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ShopViewModel(
    private val shopRepository: ShopRepository
): ViewModel() {

    var state by mutableStateOf(ShopState())

    private val eventChannel = Channel<ShopEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            getShopBooks()
        }
    }

    private suspend fun getShopBooks() {
        state = state.copy(
            isLoading = true
        )
        when (val result = shopRepository.getShopBooks()) {
            is Result.Error -> {
                eventChannel.send(ShopEvent.Error(result.error.asUiText()))
                state = state.copy(
                    error = result.error
                )
            }
            is Result.Success -> {
                state = state.copy(
                    shopBooks = result.data
                )
            }
        }
        state = state.copy(
            isLoading = false
        )
    }
}
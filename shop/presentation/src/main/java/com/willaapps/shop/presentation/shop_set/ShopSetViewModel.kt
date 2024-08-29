package com.willaapps.shop.presentation.shop_set

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.willaapps.core.domain.util.Result
import com.willaapps.core.presentation.ui.asUiText
import com.willaapps.shop.domain.ShopRepository
import com.willaapps.shop.domain.mappers.toWordSet
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ShopSetViewModel(
    private val shopRepository: ShopRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(ShopSetState())
        private set

    private val eventChannel = Channel<ShopSetEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            getBookById(checkNotNull(savedStateHandle["bookId"]))
        }
        shopRepository
            .getShopWordSets(checkNotNull(savedStateHandle["bookId"]))
            .onEach {
                state = state.copy(
                    shopWordSets = it
                )
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: ShopSetAction) {
        when (action) {
            is ShopSetAction.OnAddWordSetClick -> {
                viewModelScope.launch {
                    state = state.copy(isDownloading = true)
                    val result = shopRepository.fetchWords(
                        book = state.book,
                        wordSet = state.shopWordSets
                            .first { shopWordSet -> shopWordSet.id == action.setId }
                            .toWordSet()
                    )
                    state = state.copy(isDownloading = false)

                    when (result) {
                        is Result.Error -> {
                            eventChannel.send(ShopSetEvent.Error(result.error.asUiText()))
                        }
                        is Result.Success -> {
                            eventChannel.send(ShopSetEvent.SuccessfullyDownloaded)
                        }
                    }
                }
            }
            else -> Unit
        }
    }

    private suspend fun getBookById(bookId: String) {
        state = state.copy(
            isLoading = true
        )
        when (val result = shopRepository.getBookById(bookId)) {
            is Result.Error -> {
                eventChannel.send(ShopSetEvent.Error(result.error.asUiText()))
                state = state.copy(
                    error = result.error
                )
            }
            is Result.Success -> {
                state = state.copy(
                    book = result.data
                )
            }
        }
        state = state.copy(
            isLoading = false
        )
    }
}
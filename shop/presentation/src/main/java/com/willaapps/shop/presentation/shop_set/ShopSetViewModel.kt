package com.willaapps.shop.presentation.shop_set

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class ShopSetViewModel: ViewModel() {

    var state by mutableStateOf(ShopSetState())
        private set

    private val eventChannel = Channel<ShopSetEvent>()
    val events = eventChannel.receiveAsFlow()

    // TODO
    fun onAction(action: ShopSetAction) {

    }
}
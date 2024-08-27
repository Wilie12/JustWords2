package com.willaapps.shop.presentation.shop_set

import com.willaapps.core.presentation.ui.UiText

sealed interface ShopSetEvent {
    data class Error(val error: UiText): ShopSetEvent
    data object SuccessfullyDownloaded: ShopSetEvent
}
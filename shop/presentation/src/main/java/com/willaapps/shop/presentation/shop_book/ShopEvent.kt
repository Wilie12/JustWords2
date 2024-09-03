package com.willaapps.shop.presentation.shop_book

import com.willaapps.core.presentation.ui.UiText

interface ShopEvent {
    data class Error(val error: UiText): ShopEvent
}
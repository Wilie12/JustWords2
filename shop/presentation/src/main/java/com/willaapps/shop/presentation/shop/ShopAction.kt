package com.willaapps.shop.presentation.shop

sealed interface ShopAction {
    data object OnBackClick: ShopAction
    data class OnBookClick(val bookId: String): ShopAction
}
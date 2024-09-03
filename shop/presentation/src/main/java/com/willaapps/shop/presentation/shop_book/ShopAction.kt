package com.willaapps.shop.presentation.shop_book

sealed interface ShopAction {
    data object OnBackClick: ShopAction
    data class OnBookClick(val bookId: String): ShopAction
}
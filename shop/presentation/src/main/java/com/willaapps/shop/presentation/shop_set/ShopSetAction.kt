package com.willaapps.shop.presentation.shop_set

sealed interface ShopSetAction {
    data object OnBackClick: ShopSetAction
    data class OnAddWordSetClick(val setId: String): ShopSetAction
}
package com.willaapps.shop.presentation.shop

import com.willaapps.shop.domain.model.ShopBook

data class ShopState(
    val shopBooks: List<ShopBook> = emptyList(),
    val isLoading: Boolean = false
)

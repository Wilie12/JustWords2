package com.willaapps.shop.presentation.shop_book

import com.willaapps.core.domain.util.Error
import com.willaapps.shop.domain.model.ShopBook

data class ShopState(
    val shopBooks: List<ShopBook> = emptyList(),
    val isLoading: Boolean = false,
    val error: Error? = null
)

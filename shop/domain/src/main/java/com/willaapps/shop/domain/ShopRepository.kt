package com.willaapps.shop.domain

import com.willaapps.core.domain.util.DataError
import com.willaapps.core.domain.util.Result

interface ShopRepository {
    suspend fun getShopBooks(): Result<List<ShopBook>, DataError.Network>
}
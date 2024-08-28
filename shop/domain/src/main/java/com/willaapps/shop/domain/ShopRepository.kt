package com.willaapps.shop.domain

import com.willaapps.core.domain.util.DataError
import com.willaapps.core.domain.util.Result
import com.willaapps.core.domain.word.Book
import kotlinx.coroutines.flow.Flow

interface ShopRepository {
    suspend fun getShopBooks(): Result<List<ShopBook>, DataError.Network>
    suspend fun getBookById(bookId: String): Result<Book, DataError.Network>
    fun getShopWordSets(bookId: String): Flow<List<ShopWordSet>>
}
package com.willaapps.shop.domain

import com.willaapps.core.domain.util.DataError
import com.willaapps.core.domain.util.EmptyResult
import com.willaapps.core.domain.util.Result
import com.willaapps.core.domain.word.Book
import com.willaapps.core.domain.word.WordSet
import com.willaapps.shop.domain.model.ShopBook
import com.willaapps.shop.domain.model.ShopWordSet
import kotlinx.coroutines.flow.Flow

interface ShopRepository {
    suspend fun getShopBooks(): Result<List<ShopBook>, DataError.Network>
    suspend fun getBookById(bookId: String): Result<Book, DataError.Network>
    fun getShopWordSets(bookId: String): Flow<List<ShopWordSet>>
    suspend fun fetchWords(book: Book, wordSet: WordSet): EmptyResult<DataError>
}
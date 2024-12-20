package com.willaapps.shop.domain

import com.willaapps.core.domain.util.DataError
import com.willaapps.core.domain.util.Result
import com.willaapps.core.domain.word.Book
import com.willaapps.core.domain.word.Word
import com.willaapps.core.domain.word.WordSet

interface RemoteShopDataSource {
    suspend fun getBooks(): Result<List<Book>, DataError.Network>
    suspend fun getWordSets(): Result<List<WordSet>, DataError.Network>
    suspend fun getWordSetsById(bookId: String): Result<List<WordSet>, DataError.Network>
    suspend fun getWordsById(setId: String): Result<List<Word>, DataError.Network>
}
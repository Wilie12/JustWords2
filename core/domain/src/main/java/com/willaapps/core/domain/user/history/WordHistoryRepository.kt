package com.willaapps.core.domain.user.history

import com.willaapps.core.domain.util.DataError
import com.willaapps.core.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow

interface WordHistoryRepository {
    fun getHistoryItems(): Flow<List<WordHistory>>
    suspend fun fetchHistoryItems(): EmptyResult<DataError>
    suspend fun insertHistoryItem(wordHistory: WordHistory): EmptyResult<DataError>
    suspend fun deleteAllHistoryItems()
}
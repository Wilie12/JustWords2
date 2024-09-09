package com.willaapps.core.domain.user.history

import com.willaapps.core.domain.util.DataError
import com.willaapps.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface LocalWordHistoryDataSource {
    suspend fun insertHistoryItem(wordHistory: WordHistory): Result<String, DataError.Local>
    suspend fun insertHistoryItems(wordHistories: List<WordHistory>): Result<List<String>, DataError.Local>
    fun getHistoryItems(): Flow<List<WordHistory>>
    suspend fun deleteAllHistoryItems()
}
package com.willaapps.core.data.user.history

import com.willaapps.core.domain.user.history.LocalWordHistoryDataSource
import com.willaapps.core.domain.user.history.WordHistory
import com.willaapps.core.domain.user.history.WordHistoryRepository
import com.willaapps.core.domain.util.DataError
import com.willaapps.core.domain.util.EmptyResult
import com.willaapps.core.domain.util.Result
import com.willaapps.core.domain.util.asEmptyDataResult
import kotlinx.coroutines.flow.Flow

class OfflineFirstWordHistoryRepository(
    private val localWordHistoryDataSource: LocalWordHistoryDataSource
): WordHistoryRepository {
    override fun getHistoryItems(): Flow<List<WordHistory>> {
        return localWordHistoryDataSource.getHistoryItems()
    }

    override suspend fun fetchHistoryItems(): EmptyResult<DataError> {
        // TODO - implement fetching from server
        return Result.Success(Unit)
    }

    override suspend fun insertHistoryItem(wordHistory: WordHistory): EmptyResult<DataError> {
        when (val localResult = localWordHistoryDataSource.insertHistoryItem(wordHistory)) {
            is Result.Error -> {
                return localResult.asEmptyDataResult()
            }
            is Result.Success -> Unit
        }

        // TODO - implement posting historyItem to server
        return Result.Success(Unit)
    }

    override suspend fun deleteAllHistoryItems() {
        localWordHistoryDataSource.deleteAllHistoryItems()
    }
}
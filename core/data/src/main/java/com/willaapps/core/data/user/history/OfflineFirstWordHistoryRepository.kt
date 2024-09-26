package com.willaapps.core.data.user.history

import com.willaapps.core.domain.user.history.LocalWordHistoryDataSource
import com.willaapps.core.domain.user.history.RemoteWordHistoryDataSource
import com.willaapps.core.domain.user.history.WordHistory
import com.willaapps.core.domain.user.history.WordHistoryRepository
import com.willaapps.core.domain.util.DataError
import com.willaapps.core.domain.util.EmptyResult
import com.willaapps.core.domain.util.Result
import com.willaapps.core.domain.util.asEmptyDataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow

class OfflineFirstWordHistoryRepository(
    private val localWordHistoryDataSource: LocalWordHistoryDataSource,
    private val remoteWordHistoryDataSource: RemoteWordHistoryDataSource,
    private val applicationScope: CoroutineScope
): WordHistoryRepository {
    override fun getHistoryItems(): Flow<List<WordHistory>> {
        return localWordHistoryDataSource.getHistoryItems()
    }

    override suspend fun fetchHistoryItems(): EmptyResult<DataError> {
        return when (val result = remoteWordHistoryDataSource.getWordHistoryItems()) {
            is Result.Error -> {
                result.asEmptyDataResult()
            }
            is Result.Success -> {
                applicationScope.async {
                    localWordHistoryDataSource.insertHistoryItems(result.data).asEmptyDataResult()
                }.await()
            }
        }
    }

    override suspend fun insertHistoryItem(wordHistory: WordHistory): EmptyResult<DataError> {
        val localResult = localWordHistoryDataSource.insertHistoryItem(wordHistory)

        if (localResult !is Result.Success) {
            return localResult.asEmptyDataResult()
        }

        val wordHistoryWithId = wordHistory.copy(id = localResult.data)

        return when (val remoteResult = remoteWordHistoryDataSource.postWordHistory(wordHistoryWithId)) {
            is Result.Error -> {
                // TODO - sync data later
                Result.Error(DataError.Network.UNKNOWN)
            }
            is Result.Success -> {
                Result.Success(Unit).asEmptyDataResult()
            }
        }
    }

    override suspend fun deleteAllHistoryItems() {
        localWordHistoryDataSource.deleteAllHistoryItems()
    }
}
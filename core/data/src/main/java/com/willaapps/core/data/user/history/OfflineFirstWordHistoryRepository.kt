package com.willaapps.core.data.user.history

import com.willaapps.core.database.dao.HistoryPendingSyncDao
import com.willaapps.core.database.mappers.toWordHistory
import com.willaapps.core.domain.auth.SessionStorage
import com.willaapps.core.domain.user.history.LocalWordHistoryDataSource
import com.willaapps.core.domain.user.history.RemoteWordHistoryDataSource
import com.willaapps.core.domain.user.history.SyncHistoryScheduler
import com.willaapps.core.domain.user.history.WordHistory
import com.willaapps.core.domain.user.history.WordHistoryRepository
import com.willaapps.core.domain.util.DataError
import com.willaapps.core.domain.util.EmptyResult
import com.willaapps.core.domain.util.Result
import com.willaapps.core.domain.util.asEmptyDataResult
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import io.ktor.client.plugins.plugin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OfflineFirstWordHistoryRepository(
    private val localWordHistoryDataSource: LocalWordHistoryDataSource,
    private val remoteWordHistoryDataSource: RemoteWordHistoryDataSource,
    private val applicationScope: CoroutineScope,
    private val syncHistoryScheduler: SyncHistoryScheduler,
    private val sessionStorage: SessionStorage,
    private val historyPendingSyncDao: HistoryPendingSyncDao,
    private val client: HttpClient
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

        return when (remoteWordHistoryDataSource.postWordHistory(wordHistoryWithId)) {
            is Result.Error -> {
                applicationScope.launch {
                    syncHistoryScheduler.scheduleSync(
                        type = SyncHistoryScheduler.SyncType.CreateHistoryItem(
                            wordHistory = wordHistoryWithId
                        )
                    )
                }.join()
                Result.Success(Unit)
            }
            is Result.Success -> {
                Result.Success(Unit).asEmptyDataResult()
            }
        }
    }

    override suspend fun syncPendingHistory() {
        withContext(Dispatchers.IO) {
            val userId = sessionStorage.get()?.userId ?: return@withContext

            val createdHistoryItems = async {
                historyPendingSyncDao.getAllHistoryPendingSyncEntities(userId)
            }

            val createJobs = createdHistoryItems
                .await()
                .map {
                    launch {
                        val wordHistory = it.wordHistory.toWordHistory()

                        when (remoteWordHistoryDataSource.postWordHistory(wordHistory)) {
                            is Result.Error -> Unit
                            is Result.Success -> {
                                applicationScope.launch {
                                    historyPendingSyncDao.deleteHistoryPendingSyncEntity(it.wordHistoryId)
                                }.join()
                            }
                        }
                    }
                }
            createJobs.forEach { it.join() }
        }
    }

    override suspend fun deleteAllHistoryItems() {
        localWordHistoryDataSource.deleteAllHistoryItems()
    }

    override suspend fun logout() {
        client.plugin(Auth).providers.filterIsInstance<BearerAuthProvider>()
            .firstOrNull()
            ?.clearToken()
    }
}
package com.willaapps.word.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.willaapps.core.database.dao.HistoryPendingSyncDao
import com.willaapps.core.database.mappers.toWordHistory
import com.willaapps.core.domain.user.history.RemoteWordHistoryDataSource

class CreateHistoryWorker(
    context: Context,
    private val params: WorkerParameters,
    private val remoteWordHistoryDataSource: RemoteWordHistoryDataSource,
    private val pendingSyncDao: HistoryPendingSyncDao
): CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

        if (runAttemptCount >= 5) {
            return Result.failure()
        }

        val pendingHistoryId = params.inputData.getString(HISTORY_ID) ?: return Result.failure()
        val pendingHistoryEntity = pendingSyncDao.getHistoryPendingSyncEntity(pendingHistoryId)
            ?: return Result.failure()

        val wordHistory = pendingHistoryEntity.wordHistory.toWordHistory()

        return when (val result = remoteWordHistoryDataSource.postWordHistory(wordHistory)) {
            is com.willaapps.core.domain.util.Result.Error -> {
                result.error.toWorkerResult()
            }
            is com.willaapps.core.domain.util.Result.Success -> {
                pendingSyncDao.deleteHistoryPendingSyncEntity(pendingHistoryId)
                Result.success()
            }
        }
    }

    companion object {
        const val HISTORY_ID = "HISTORY_ID"
    }
}
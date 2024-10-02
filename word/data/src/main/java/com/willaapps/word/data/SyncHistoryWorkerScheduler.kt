package com.willaapps.word.data

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.await
import com.willaapps.core.database.dao.HistoryPendingSyncDao
import com.willaapps.core.database.entity.HistoryPendingSyncEntity
import com.willaapps.core.database.mappers.toHistoryEntity
import com.willaapps.core.domain.auth.SessionStorage
import com.willaapps.core.domain.user.history.SyncHistoryScheduler
import com.willaapps.core.domain.user.history.WordHistory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.toJavaDuration

class SyncHistoryWorkerScheduler(
    context: Context,
    private val sessionStorage: SessionStorage,
    private val pendingSyncDao: HistoryPendingSyncDao,
    private val applicationScope: CoroutineScope
): SyncHistoryScheduler {

    private val workManager = WorkManager.getInstance(context)

    override suspend fun scheduleSync(type: SyncHistoryScheduler.SyncType) {
        when (type) {
            is SyncHistoryScheduler.SyncType.FetchHistoryItems -> scheduleFetchRunWorker(type.interval)
            is SyncHistoryScheduler.SyncType.CreateHistoryItem -> scheduleCreateHistoryWorker(type.wordHistory)
        }
    }

    override suspend fun cancelAllSyncs() {
        workManager.cancelAllWork().await()
    }

    private suspend fun scheduleFetchRunWorker(interval: Duration) {
        val isSyncScheduled = withContext(Dispatchers.IO) {
            workManager
                .getWorkInfosByTag("sync_work")
                .get()
                .isNotEmpty()
        }
        if (isSyncScheduled) return

        val workRequest = PeriodicWorkRequestBuilder<FetchHistoryWorker>(
            repeatInterval = interval.toJavaDuration()
        )
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .setBackoffCriteria(
                backoffPolicy = BackoffPolicy.EXPONENTIAL,
                backoffDelay = 2000L,
                timeUnit = TimeUnit.MILLISECONDS
            )
            .setInitialDelay(
                duration = 30,
                timeUnit = TimeUnit.MINUTES
            )
            .addTag("sync_work")
            .build()

        workManager.enqueue(workRequest).await()
    }

    private suspend fun scheduleCreateHistoryWorker(wordHistory: WordHistory) {

        val userId = sessionStorage.get()?.userId ?: return
        val pendingHistory = HistoryPendingSyncEntity(
            wordHistory = wordHistory.toHistoryEntity(),
            userId = userId
        )
        pendingSyncDao.upsertHistoryPendingSyncEntity(pendingHistory)

        val workRequest = OneTimeWorkRequestBuilder<CreateHistoryWorker>()
            .addTag("create_work")
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .setBackoffCriteria(
                backoffPolicy = BackoffPolicy.EXPONENTIAL,
                backoffDelay = 2000L,
                timeUnit = TimeUnit.MILLISECONDS
            )
            .setInputData(
                Data.Builder()
                    .putString(CreateHistoryWorker.HISTORY_ID, pendingHistory.wordHistoryId)
                    .build()
            )
            .build()

        applicationScope.launch {
            workManager.enqueue(workRequest).await()
        }.join()
    }
}
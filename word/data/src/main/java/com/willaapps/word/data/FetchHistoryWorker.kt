package com.willaapps.word.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.willaapps.core.domain.user.history.WordHistoryRepository

class FetchHistoryWorker(
    context: Context,
    params: WorkerParameters,
    private val historyRepository: WordHistoryRepository
): CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

        if (runAttemptCount >= 5) {
            return Result.failure()
        }

        return when (val result = historyRepository.fetchHistoryItems()) {
            is com.willaapps.core.domain.util.Result.Error -> {
                result.error.toWorkerResult()
            }
            is com.willaapps.core.domain.util.Result.Success -> {
                Result.success()
            }
        }
    }
}
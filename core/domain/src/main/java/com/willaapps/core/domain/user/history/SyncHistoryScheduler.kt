package com.willaapps.core.domain.user.history

import kotlin.time.Duration

interface SyncHistoryScheduler {

    suspend fun scheduleSync(type: SyncType)
    suspend fun cancelAllSyncs()

    sealed interface SyncType {
        data class FetchHistoryItems(val interval: Duration): SyncType
        class CreateHistoryItem(val wordHistory: WordHistory): SyncType
    }
}
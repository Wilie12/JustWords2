package com.willaapps.word.data.di

import com.willaapps.core.domain.user.history.SyncHistoryScheduler
import com.willaapps.word.data.CreateHistoryWorker
import com.willaapps.word.data.FetchHistoryWorker
import com.willaapps.word.data.SyncHistoryWorkerScheduler
import com.willaapps.word.data.previous.PreviousWordStorageImpl
import com.willaapps.word.domain.PreviousWordStorage
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val wordDataModule = module {

    workerOf(::CreateHistoryWorker)
    workerOf(::FetchHistoryWorker)

    singleOf(::PreviousWordStorageImpl).bind<PreviousWordStorage>()
    singleOf(::SyncHistoryWorkerScheduler).bind<SyncHistoryScheduler>()
}
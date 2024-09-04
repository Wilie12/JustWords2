package com.willaapps.word.data.di

import com.willaapps.word.data.PreviousWordStorageImpl
import com.willaapps.word.domain.PreviousWordStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val wordDataModule = module {

    singleOf(::PreviousWordStorageImpl).bind<PreviousWordStorage>()
}
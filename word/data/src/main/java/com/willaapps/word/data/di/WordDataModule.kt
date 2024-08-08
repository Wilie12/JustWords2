package com.willaapps.word.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.willaapps.word.data.PreviousWordStorageImpl
import com.willaapps.word.domain.PreviousWordStorage
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val wordDataModule = module {

    singleOf(::PreviousWordStorageImpl).bind<PreviousWordStorage>()
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            produceFile = {
                androidApplication().preferencesDataStoreFile("DATASTORE_PREFERENCES")
            }
        )
    }
}
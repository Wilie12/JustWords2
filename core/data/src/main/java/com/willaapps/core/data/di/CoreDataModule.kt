package com.willaapps.core.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.willaapps.core.data.auth.EncryptedSessionStorage
import com.willaapps.core.data.networking.HttpClientFactory
import com.willaapps.core.data.user.KtorUserInfoRepository
import com.willaapps.core.data.user.UserStorageImpl
import com.willaapps.core.data.user.history.KtorWordHistoryDataSource
import com.willaapps.core.data.user.history.OfflineFirstWordHistoryRepository
import com.willaapps.core.domain.auth.SessionStorage
import com.willaapps.core.domain.user.UserInfoRepository
import com.willaapps.core.domain.user.UserStorage
import com.willaapps.core.domain.user.history.RemoteWordHistoryDataSource
import com.willaapps.core.domain.user.history.WordHistoryRepository
import io.ktor.client.HttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    single<HttpClient> {
        HttpClientFactory(get()).build()
    }
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            produceFile = {
                androidApplication().preferencesDataStoreFile("DATASTORE_PREFERENCES")
            }
        )
    }
    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()
    singleOf(::UserStorageImpl).bind<UserStorage>()
    singleOf(::KtorUserInfoRepository).bind<UserInfoRepository>()
    singleOf(::KtorWordHistoryDataSource).bind<RemoteWordHistoryDataSource>()
    singleOf(::OfflineFirstWordHistoryRepository).bind<WordHistoryRepository>()
}
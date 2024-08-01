package com.willaapps.core.data.di

import com.willaapps.core.data.auth.EncryptedSessionStorage
import com.willaapps.core.data.networking.HttpClientFactory
import com.willaapps.core.data.word.LocalWordRepositoryImpl
import com.willaapps.core.domain.SessionStorage
import com.willaapps.core.domain.word.LocalWordRepository
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    single<HttpClient> {
        HttpClientFactory(get()).build()
    }
    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()

    singleOf(::LocalWordRepositoryImpl).bind<LocalWordRepository>()
}
package com.willaapps.core.data.di

import com.willaapps.core.data.networking.HttpClientFactory
import io.ktor.client.HttpClient
import org.koin.dsl.module

val coreDataModule = module {
    single<HttpClient> {
        HttpClientFactory().build()
    }
}
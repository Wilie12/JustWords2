package com.willaapps.shop.data.di

import com.willaapps.shop.data.KtorRemoteShopDataSource
import com.willaapps.shop.data.ShopRepositoryImpl
import com.willaapps.shop.domain.RemoteShopDataSource
import com.willaapps.shop.domain.ShopRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val shopDataModule = module {
    singleOf(::KtorRemoteShopDataSource).bind<RemoteShopDataSource>()
    singleOf(::ShopRepositoryImpl).bind<ShopRepository>()
}
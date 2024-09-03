package com.willaapps.shop.presentation.di

import com.willaapps.shop.presentation.shop_book.ShopViewModel
import com.willaapps.shop.presentation.shop_set.ShopSetViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val shopPresentationModule = module {
    viewModelOf(::ShopViewModel)
    viewModelOf(::ShopSetViewModel)
}
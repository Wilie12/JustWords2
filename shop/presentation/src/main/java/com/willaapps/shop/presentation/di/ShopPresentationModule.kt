package com.willaapps.shop.presentation.di

import com.willaapps.shop.presentation.shop.ShopViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val shopPresentationModule = module {
    viewModelOf(::ShopViewModel)
}
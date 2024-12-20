package com.willaapps.auth.presentation.di

import com.willaapps.auth.presentation.login.LoginViewModel
import com.willaapps.auth.presentation.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val authPresentationModule = module {
    viewModelOf(::RegisterViewModel)
    viewModelOf(::LoginViewModel)
}
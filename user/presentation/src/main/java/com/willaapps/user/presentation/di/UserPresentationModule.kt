package com.willaapps.user.presentation.di

import com.willaapps.user.presentation.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val userPresentationModule = module {
    viewModelOf(::ProfileViewModel)
}
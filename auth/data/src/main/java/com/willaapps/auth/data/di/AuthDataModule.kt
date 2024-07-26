package com.willaapps.auth.data.di

import com.willaapps.auth.data.EmailPatternValidator
import com.willaapps.auth.domain.PatternValidator
import com.willaapps.auth.domain.UserDataValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val authDataModule = module {
    single<PatternValidator> {
        EmailPatternValidator
    }
    singleOf(::UserDataValidator)
}
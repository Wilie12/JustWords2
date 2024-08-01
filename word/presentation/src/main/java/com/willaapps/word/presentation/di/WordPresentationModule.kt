package com.willaapps.word.presentation.di

import com.willaapps.word.presentation.start.StartViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val wordPresentationModule = module {
    viewModelOf(::StartViewModel)
}
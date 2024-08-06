package com.willaapps.word.presentation.di

import com.willaapps.word.presentation.set_list.SetListViewModel
import com.willaapps.word.presentation.start.StartViewModel
import com.willaapps.word.presentation.word.WordViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val wordPresentationModule = module {
    viewModelOf(::StartViewModel)
    viewModelOf(::SetListViewModel)
    viewModelOf(::WordViewModel)
}
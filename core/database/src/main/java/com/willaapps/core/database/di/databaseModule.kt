package com.willaapps.core.database.di

import androidx.room.Room
import com.willaapps.core.database.WordDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            WordDatabase::class.java,
            WordDatabase.DATABASE_NAME
        ).build()
    }

    single { get<WordDatabase>().wordDao }
}
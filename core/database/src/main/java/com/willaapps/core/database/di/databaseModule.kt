package com.willaapps.core.database.di

import androidx.room.Room
import com.willaapps.core.database.RoomLocalWordDataSource
import com.willaapps.core.database.WordDatabase
import com.willaapps.core.domain.word.LocalWordDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
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

    singleOf(::RoomLocalWordDataSource).bind<LocalWordDataSource>()
}
package com.willaapps.justwords2

import android.app.Application
import com.willaapps.auth.data.di.authDataModule
import com.willaapps.auth.presentation.di.authViewModelModule
import com.willaapps.core.data.di.coreDataModule
import com.willaapps.core.database.di.databaseModule
import com.willaapps.justwords2.di.appModule
import com.willaapps.word.presentation.di.wordPresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class JustWords2App: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@JustWords2App)
            modules(
                appModule,
                authDataModule,
                authViewModelModule,
                coreDataModule,
                databaseModule,
                wordPresentationModule
            )
        }
    }
}
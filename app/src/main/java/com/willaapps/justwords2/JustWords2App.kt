package com.willaapps.justwords2

import android.app.Application
import com.willaapps.auth.data.di.authDataModule
import com.willaapps.auth.presentation.di.authViewModelModule
import com.willaapps.justwords2.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class JustWords2App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@JustWords2App)
            modules(
                appModule,
                authDataModule,
                authViewModelModule
            )
        }
    }
}
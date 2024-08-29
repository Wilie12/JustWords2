package com.willaapps.justwords2

import android.app.Application
import com.willaapps.auth.data.di.authDataModule
import com.willaapps.auth.presentation.di.authPresentationModule
import com.willaapps.core.data.di.coreDataModule
import com.willaapps.core.database.di.databaseModule
import com.willaapps.justwords2.di.appModule
import com.willaapps.shop.data.di.shopDataModule
import com.willaapps.shop.presentation.di.shopPresentationModule
import com.willaapps.word.data.di.wordDataModule
import com.willaapps.word.presentation.di.wordPresentationModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class JustWords2App: Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

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
                authPresentationModule,
                coreDataModule,
                databaseModule,
                wordPresentationModule,
                wordDataModule,
                shopPresentationModule,
                shopDataModule
            )
        }
    }
}
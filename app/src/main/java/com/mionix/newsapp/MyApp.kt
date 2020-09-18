package com.mionix.newsapp

import android.app.Application
import com.mionix.baseapp.di.repositoryModule
import com.mionix.baseapp.di.retrofitModule
import com.mionix.baseapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // start Koin!
        startKoin {
            androidLogger(Level.DEBUG)
            // Android context
            androidContext(this@MyApp)
            // modules
            modules(arrayListOf(retrofitModule,viewModelModule,repositoryModule))
        }
    }
}
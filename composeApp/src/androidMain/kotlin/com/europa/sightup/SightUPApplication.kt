package com.europa.sightup

import android.app.Application
import com.europa.sightup.di.initializeKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class SightUPApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(this@SightUPApplication)
        }
    }
}
package com.europa.sightup

import android.app.Application
import android.util.Log
import com.europa.sightup.data.local.KVaultStorage
import com.europa.sightup.di.initializeKoin
import com.europa.sightup.utils.FIREBASE_NOTIFICATION_TOKEN
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import multiplatform.network.cmptoast.AppContext
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class SightUPApplication : Application() {

    private val kVaultStorage: KVaultStorage by inject()

    override fun onCreate() {
        super.onCreate()
        // Provide context for CMPToast lib (multiplatform.network.cmptoast)
        AppContext.set(this)

        initializeKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(this@SightUPApplication)
        }

        NotifierManager.initialize(
            configuration = NotificationPlatformConfiguration.Android(
                notificationIconResId = R.drawable.ic_launcher_foreground,
            )
        )

        NotifierManager.addListener(object : NotifierManager.Listener {
            override fun onNewToken(token: String) {
                Log.d(FIREBASE_NOTIFICATION_TOKEN, token)
                kVaultStorage.set(FIREBASE_NOTIFICATION_TOKEN, token)
            }
        })
    }
}
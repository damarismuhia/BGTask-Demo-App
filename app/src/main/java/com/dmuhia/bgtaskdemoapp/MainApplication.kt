package com.dmuhia.bgtaskdemoapp

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.Configuration
import com.dmuhia.bgtaskdemoapp.data.worker.CustomWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MainApplication() : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: CustomWorkerFactory
    init {
        appInstance = this
    }
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(Log.INFO)
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }
    companion object {
        private var appInstance: MainApplication? = null
        fun applicationContext(): Context? {
            return appInstance?.applicationContext
        }
    }
}




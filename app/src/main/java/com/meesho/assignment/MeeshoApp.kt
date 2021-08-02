package com.meesho.assignment

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MeeshoApp : Application(), Configuration.Provider {

    @Inject
    lateinit var appLifeCycleObserver: AppLifeCycleObserver

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(appLifeCycleObserver)
    }

    override fun getWorkManagerConfiguration(): Configuration {
      return  Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }
}
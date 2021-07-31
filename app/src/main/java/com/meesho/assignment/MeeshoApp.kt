package com.meesho.assignment

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MeeshoApp:Application() {

    @Inject
    lateinit var appLifeCycleObserver: AppLifeCycleObserver

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(appLifeCycleObserver)
    }
}
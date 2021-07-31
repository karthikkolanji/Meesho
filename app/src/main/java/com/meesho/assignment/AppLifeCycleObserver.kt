package com.meesho.assignment

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import javax.inject.Inject


class AppLifeCycleObserver @Inject constructor(): LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onEnterForeground() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onEnterBackground() {

    }

}
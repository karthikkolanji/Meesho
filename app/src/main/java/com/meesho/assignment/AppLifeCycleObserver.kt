package com.meesho.assignment

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.meesho.jakewarton.domain.ShowNotification
import javax.inject.Inject


class AppLifeCycleObserver @Inject constructor(private val showNotification: ShowNotification) :
    LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onEnterForeground() {
        showNotification.cancel()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onEnterBackground() {
        showNotification.show()
    }

}
package com.meesho.jakewarton.domain

import androidx.work.*
import com.meesho.base.extensions.enableWorkerLogging
import com.meesho.jakewarton.data.background.NotificationWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShowNotification @Inject constructor(private val workManager: WorkManager) {

    fun show() {
        val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                    .build()
            )
            .addTag(NOTIFICATION_WORK)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 1, TimeUnit.SECONDS)
            .build()
            .enableWorkerLogging()

        if (workManager!=null){
            workManager
                .beginUniqueWork(NOTIFICATION_WORK, ExistingWorkPolicy.REPLACE, workRequest)
                .enqueue()
        }
    }

    fun cancel() {
        if (workManager!=null){
            workManager.cancelUniqueWork(NOTIFICATION_WORK)
        }
    }

    companion object {
        const val NOTIFICATION_WORK = "notification_work"
    }

}
package com.meesho.jakewarton.domain

import androidx.work.*
import com.meesho.base.extensions.enableWorkerLogging
import com.meesho.jakewarton.data.background.BookSeatWorker
import dagger.hilt.android.scopes.ViewModelScoped
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ViewModelScoped
class BookSeat @Inject constructor(private val workManager: WorkManager) {

    suspend fun bookSeat(scanResult: String) {
        val workRequest = OneTimeWorkRequestBuilder<BookSeatWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                    .build()
            )
            .setInputData(Data.Builder().putString(SCAN_RESULT, scanResult).build())
            .addTag(TIMER_WORK)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 1, TimeUnit.SECONDS)
            .build()
            .enableWorkerLogging()

        workManager
            .beginUniqueWork(TIMER_WORK, ExistingWorkPolicy.REPLACE, workRequest)
            .enqueue()
    }

    companion object {
        const val SCAN_RESULT = "scan_result"
        const val TIMER_WORK="timer_work"
    }

}
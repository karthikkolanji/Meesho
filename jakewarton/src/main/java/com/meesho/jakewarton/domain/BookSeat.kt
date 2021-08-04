package com.meesho.jakewarton.domain

import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.meesho.jakewarton.data.background.BookSeatWorker
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class BookSeat @Inject constructor(private val workManager: WorkManager) {

    suspend fun bookSeat(scanResult: String) {
        val workerRequest = OneTimeWorkRequest.Builder(BookSeatWorker::class.java)
        val data = Data.Builder()
        data.putString(SCAN_RESULT, scanResult)
        workerRequest.setInputData(data.build())
        workManager.enqueueUniqueWork(TIMER_WORK,ExistingWorkPolicy.REPLACE,workerRequest.build())
    }

    companion object {
        const val SCAN_RESULT = "scan_result"
        const val TIMER_WORK="timer_work"
    }

}
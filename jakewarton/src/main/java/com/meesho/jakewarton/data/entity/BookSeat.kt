package com.meesho.jakewarton.data.entity

import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.meesho.jakewarton.data.background.BookSeatWorker
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class BookSeat @Inject constructor(private val workManager: WorkManager) {

    suspend fun bookSeat(scanResult: String) {
        val worker = OneTimeWorkRequest.Builder(BookSeatWorker::class.java)
        val data = Data.Builder()
        data.putString(SCAN_RESULT, scanResult)
        worker.setInputData(data.build())
        workManager.enqueue(worker.build())
    }

    companion object {
        const val SCAN_RESULT = "scan_result"
    }

}
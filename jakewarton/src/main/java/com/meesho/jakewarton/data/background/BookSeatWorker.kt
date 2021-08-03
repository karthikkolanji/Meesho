package com.meesho.jakewarton.data.background

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.meesho.jakewarton.data.entity.BookSeat
import com.meesho.jakewarton.domain.SaveQrScanResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class BookSeatWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val saveQrScanResult: SaveQrScanResult
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val scanResult = inputData.getString(BookSeat.SCAN_RESULT)
        saveQrScanResult.saveTime(scanResult!!)
        return Result.success()
    }
}

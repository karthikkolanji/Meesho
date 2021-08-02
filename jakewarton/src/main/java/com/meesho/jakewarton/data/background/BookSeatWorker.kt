package com.meesho.jakewarton.data.background

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.meesho.jakewarton.data.db.BookSeatDao
import com.meesho.jakewarton.data.entity.BookSeat
import com.meesho.jakewarton.domain.ParseScanResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class BookSeatWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val dao: BookSeatDao,
    private val parseScanResult: ParseScanResult
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val data = inputData.getString(BookSeat.SCAN_RESULT)
        val scanResult = parseScanResult.parse(data!!)
        dao.insert(scanResult)
        return Result.success()
    }
}

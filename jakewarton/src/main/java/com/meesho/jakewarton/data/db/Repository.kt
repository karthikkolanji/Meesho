package com.meesho.jakewarton.data.db

import com.meesho.base.di.DispatcherProvider
import com.meesho.jakewarton.data.entity.QRScanResult
import com.meesho.jakewarton.data.entity.Timer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val dao: BookSeatDao,
    private val dispatcherProvider: DispatcherProvider
) {

    suspend fun saveQrScanResult(scanResult: QRScanResult) {
        val activeSession = scanResult.copy(session_status = true)
        dao.insert(activeSession)
    }

    suspend fun updateTime(timer: Timer) {
        val scanResult = withContext(dispatcherProvider.io()) {
            dao.getQrResult()
        }
        val locationId = scanResult.location_id
        withContext(dispatcherProvider.io()) {
            dao.updateTime(timer.hour, timer.minute, timer.seconds, locationId)
        }
    }

    suspend fun getElapsedTime(): Flow<QRScanResult> {
        return dao.getElapsedTime()
            .flowOn(dispatcherProvider.io())
            .distinctUntilChanged()
    }

    suspend fun getSessionStatus(): Flow<Boolean> {
        return dao.getSessionStatus()
            .flowOn(dispatcherProvider.io())
            .distinctUntilChanged()
    }

}
package com.meesho.jakewarton.data.db

import com.meesho.base.di.DispatcherProvider
import com.meesho.jakewarton.data.entity.QRScanResult
import com.meesho.jakewarton.data.entity.Session
import com.meesho.jakewarton.data.entity.Timer
import com.meesho.jakewarton.data.remote.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val dao: BookSeatDao,
    private val apiService: ApiService,
    private val dispatcherProvider: DispatcherProvider
) {

    suspend fun getSession(): QRScanResult {
        return withContext(dispatcherProvider.io()) {
            dao.getQrResult()
        }
    }

    suspend fun saveQrScanResult(scanResult: QRScanResult, startTime: Long) {
        val activeSession = scanResult.copy(session_status = true, start_time = startTime)
        dao.save(activeSession)
    }

    suspend fun updateTime(timer: Timer) {
        val scanResult = withContext(dispatcherProvider.io()) {
            dao.getQrResult()
        }
        withContext(dispatcherProvider.io()) {
            dao.updateTime(timer.hour, timer.minute, timer.seconds, scanResult.location_id)
        }
    }

    suspend fun updateAmount(price: Float) {
        val scanResult = withContext(dispatcherProvider.io()) {
            dao.getQrResult()
        }
        withContext(dispatcherProvider.io()) {
            dao.updateAmount(price, scanResult.location_id)
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

    suspend fun endSession(endTime: Long) {
        val scanResult = withContext(dispatcherProvider.io()) {
            dao.getQrResult()
        }
        withContext(dispatcherProvider.io()) {
            dao.endSession(active = false, endTime = endTime, id = scanResult.location_id)
        }
    }

    suspend fun submitSession() {
        val session = withContext(dispatcherProvider.io()) {
            dao.getQrResult()
        }
        val totalTimeSpent=(session.end_time - session.start_time) / 60000
        apiService.submitSession(Session(session.location_id,totalTimeSpent,session.total_price))
    }

    suspend fun deleteSession()  {
        withContext(dispatcherProvider.io()) {
            dao.deleteSession()
        }
    }

}
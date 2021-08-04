package com.meesho.jakewarton.domain

import com.meesho.jakewarton.data.db.Repository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveQrScanResult @Inject constructor(
    private val repository: Repository,
    private val parseScanResult: ParseScanResult,
    private val sessionTimer: SessionTimer
) {

    suspend fun saveTime(scanResult: String) {
        repository.saveQrScanResult(scanResult = parseScanResult.parse(scanResult),startTime = System.currentTimeMillis())
        sessionTimer.start()
    }
}
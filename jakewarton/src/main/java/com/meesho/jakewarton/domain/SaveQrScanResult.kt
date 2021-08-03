package com.meesho.jakewarton.domain

import com.meesho.jakewarton.data.db.Repository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveQrScanResult @Inject constructor(
    private val repository: Repository,
    private val parseScanResult: ParseScanResult,
    private val startTimer: StartTimer
) {

    suspend fun saveTime(scanResult: String) {
        repository.saveQrScanResult(parseScanResult.parse(scanResult))
        startTimer.start()
    }
}
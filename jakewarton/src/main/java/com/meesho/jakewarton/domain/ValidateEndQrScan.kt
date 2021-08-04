package com.meesho.jakewarton.domain

import com.meesho.jakewarton.data.db.Repository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ValidateEndQrScan @Inject constructor(
    private val repository: Repository,
    private val parseScanResult: ParseScanResult
) {

    suspend fun isValid(endQRrScanResult: String): Boolean {
        val activeSession = repository.getSession()
        val endSession = parseScanResult.parse(endQRrScanResult)
        return activeSession.location_id == endSession.location_id
    }
}
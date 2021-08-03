package com.meesho.jakewarton.domain

import com.meesho.jakewarton.data.db.Repository
import com.meesho.jakewarton.data.entity.QRScanResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetElapsedTime @Inject constructor(private val repository: Repository) {

    suspend fun get(): Flow<QRScanResult?> {
       return repository.getElapsedTime()
    }
}
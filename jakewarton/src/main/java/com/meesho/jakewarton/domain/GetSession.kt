package com.meesho.jakewarton.domain

import com.meesho.jakewarton.data.db.Repository
import com.meesho.jakewarton.data.entity.QRScanResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSession @Inject constructor(private val repository: Repository) {

    suspend fun get(): QRScanResult {
        return repository.getSession()
    }
}
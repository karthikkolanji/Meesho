package com.meesho.jakewarton.domain

import com.google.gson.Gson
import com.meesho.jakewarton.data.entity.QRScanResult
import com.meesho.jakewarton.utils.Utils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ParseScanResult @Inject constructor(
    private val utils: Utils,
) {

    suspend fun parse(value: String):QRScanResult {
        return Gson().fromJson(utils.formatBarcodeResult(value), QRScanResult::class.java)
    }
}
package com.meesho.jakewarton.domain

import com.google.gson.Gson
import com.meesho.jakewarton.data.db.BookSeatDao
import com.meesho.jakewarton.data.entity.QRScanResult
import com.meesho.jakewarton.utils.Utils
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ParseScanResult @Inject constructor(
    private val utils: Utils,
    private val bookSeatDao: BookSeatDao
) {

    suspend fun parse(value: String) {
        val result=Gson().fromJson(utils.formatBarcodeResult(value), QRScanResult::class.java)
        bookSeatDao.insert(result)
    }
}
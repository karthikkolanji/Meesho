package com.meesho.jakewarton.ui

import androidx.lifecycle.ViewModel
import com.meesho.base.di.DispatcherProvider
import com.meesho.jakewarton.domain.ParseScanResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookSeatViewModel @Inject constructor(
    private val parseScanResult: ParseScanResult,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    suspend fun parseScanResult(scanResult: String) {
        parseScanResult.parse(scanResult)
    }
}
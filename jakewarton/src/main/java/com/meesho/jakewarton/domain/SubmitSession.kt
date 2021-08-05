package com.meesho.jakewarton.domain

import com.meesho.base.utils.NetworkError
import com.meesho.jakewarton.data.db.Repository
import com.meesho.jakewarton.utils.ScanError
import com.meesho.jakewarton.utils.Utils
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class SubmitSession @Inject constructor(
    private val repository: Repository,
    private val calculatePrice: CalculatePrice,
    private val sessionTimer: SessionTimer,
    private val bookSeat: BookSeat,
    private val validateEndQrScan: ValidateEndQrScan,
    private val utils: Utils
) {

    suspend fun submit(endQrrScanResult: String) {
        if (utils.isNetworkConnected()){
            if (validateEndQrScan.isValid(endQrrScanResult)) {
                endSession()
            } else {
                throw ScanError()
            }
        }
        else{
            throw NetworkError()
        }
    }

    private suspend fun endSession() {
        // first stops the timer
        sessionTimer.stopTimer()

        // stop the worker
        bookSeat.cancel()

        // ends the session by updating values in db
        repository.endSession(System.currentTimeMillis())

        // calculates the total amount and updates to db
        calculatePrice.calculate()

        // gets data from db & call api for submit
        repository.submitSession()
    }

}
package com.meesho.jakewarton.domain

import androidx.work.WorkManager
import com.meesho.jakewarton.data.db.Repository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class SubmitSession @Inject constructor(
    private val repository: Repository,
    private val calculatePrice: CalculatePrice,
    private val sessionTimer: SessionTimer,
    private val workManager: WorkManager
) {

    suspend fun submit() {
        // first stops the timer
        sessionTimer.stopTimer()

        // stop the worker
        workManager.cancelUniqueWork(BookSeat.TIMER_WORK)

        // ends the session by updating values in db
        repository.endSession(System.currentTimeMillis())

        // calculates the total amount and updates to db
        calculatePrice.calculate()

        // gets data from db & call api for submit
        repository.submitSession()
    }

}
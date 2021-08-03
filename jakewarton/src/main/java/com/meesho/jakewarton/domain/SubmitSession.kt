package com.meesho.jakewarton.domain

import com.meesho.jakewarton.data.db.Repository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class SubmitSession @Inject constructor(
    private val repository: Repository,
    private val calculatePrice: CalculatePrice,
    private val startTimer: StartTimer
) {

    suspend fun submit() {
        // first stops the timer
        startTimer.stopTimer()

        // ends the session by updating values in db
        repository.endSession(System.currentTimeMillis())

        // calculates the total amount and updates to db
        calculatePrice.calculate()

        // gets data from db & call api for submit
        repository.submitSession()
    }

}
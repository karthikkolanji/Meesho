package com.meesho.jakewarton.domain

import com.meesho.jakewarton.data.db.Repository
import javax.inject.Inject

class CalculatePrice @Inject constructor(private val repository: Repository) {


    suspend fun calculate() {
        val session = repository.getSession()
        val duration = (session.end_time - session.start_time) / 60000
        val amount = session.price_per_min * duration
        repository.updateAmount(amount)
    }
}
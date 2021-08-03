package com.meesho.jakewarton.data.entity

import androidx.annotation.Keep

@Keep
data class Session(
    val location_id: String,
    val time_spent: Long,
    val end_time: Float
)

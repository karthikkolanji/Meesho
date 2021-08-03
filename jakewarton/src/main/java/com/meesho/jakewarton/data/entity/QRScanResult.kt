package com.meesho.jakewarton.data.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity
data class QRScanResult(
    @PrimaryKey val location_id: String,
    val location_details: String? = null,
    val price_per_min: Float = 0F,
    val hour: Long = 0,
    val minute: Long = 0,
    val seconds: Long = 0,
    val session_status: Boolean = false,
    val start_time: Long = 0L,
    val end_time: Long = 0L,
    val total_price: Float = 0F
)

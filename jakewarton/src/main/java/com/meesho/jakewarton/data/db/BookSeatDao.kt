package com.meesho.jakewarton.data.db

import androidx.room.Dao
import androidx.room.Insert
import com.meesho.jakewarton.data.entity.QRScanResult

@Dao
interface BookSeatDao {
    @Insert
    suspend fun insert(qrScanResult: QRScanResult)

}
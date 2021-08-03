package com.meesho.jakewarton.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.meesho.jakewarton.data.entity.QRScanResult
import kotlinx.coroutines.flow.Flow

@Dao
interface BookSeatDao {
    @Insert
    suspend fun insert(qrScanResult: QRScanResult)

    @Query("SELECT * FROM QRScanResult")
    suspend fun getQrResult(): QRScanResult

    @Query("UPDATE QRScanResult SET hour=:hour,minute=:minute,seconds=:seconds WHERE location_id = :id")
    suspend fun updateTime(hour: Long, minute: Long, seconds: Long, id: String)

    @Query("SELECT * FROM QRScanResult")
    fun getElapsedTime(): Flow<QRScanResult>

    @Query("SELECT session_status FROM QRScanResult")
    fun getSessionStatus(): Flow<Boolean>

    @Transaction
    suspend fun endSession(active: Boolean, id: String) {
        updateSession(active, id)
        deleteSession()
    }


    @Query("UPDATE QRScanResult SET session_status=:active WHERE location_id = :id")
    suspend fun updateSession(active: Boolean, id: String)

    @Query("DELETE FROM QRScanResult")
    suspend fun deleteSession()
}
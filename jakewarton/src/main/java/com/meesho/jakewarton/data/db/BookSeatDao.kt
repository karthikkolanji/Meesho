package com.meesho.jakewarton.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.meesho.jakewarton.data.entity.QRScanResult
import kotlinx.coroutines.flow.Flow

@Dao
interface BookSeatDao {

    @Transaction
    suspend fun save(qrScanResult: QRScanResult) {
        deleteSession()
        insert(qrScanResult)
    }

    @Insert
    suspend fun insert(qrScanResult: QRScanResult)

    @Query("SELECT * FROM QRScanResult")
    suspend fun getQrResult(): QRScanResult

    @Query("UPDATE QRScanResult SET hour=:hour,minute=:minute,seconds=:seconds WHERE location_id = :id")
    suspend fun updateTime(hour: Long, minute: Long, seconds: Long, id: String)

    @Query("UPDATE QRScanResult SET total_price=:amount WHERE location_id = :id")
    suspend fun updateAmount(amount: Float, id: String)

    @Query("SELECT * FROM QRScanResult")
    fun getElapsedTime(): Flow<QRScanResult>

    @Query("SELECT session_status FROM QRScanResult")
    fun getSessionStatus(): Flow<Boolean>

//    @Transaction
//    suspend fun endSession(active: Boolean, id: String,endTime:Long) {
//        updateSession(active, id)
//        deleteSession()
//    }


    @Query("UPDATE QRScanResult SET end_time=:endTime, session_status=:active WHERE location_id = :id")
    suspend fun endSession(active: Boolean, endTime: Long, id: String)

    @Query("DELETE FROM QRScanResult")
    suspend fun deleteSession()
}
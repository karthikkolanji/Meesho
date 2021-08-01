package com.meesho.jakewarton.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.meesho.jakewarton.data.entity.QRScanResult

@Database(entities = [QRScanResult::class], version = 1)
abstract class BookSeatDatabase : RoomDatabase() {
    abstract fun bookSeatDao(): BookSeatDao
}
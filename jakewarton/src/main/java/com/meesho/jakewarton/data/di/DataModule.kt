package com.meesho.jakewarton.data.di

import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.meesho.jakewarton.data.db.BookSeatDao
import com.meesho.jakewarton.data.db.BookSeatDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideDao(bookSeatDatabase: BookSeatDatabase): BookSeatDao {
        return bookSeatDatabase.bookSeatDao()
    }

    @Provides
    @Singleton
    fun provideDB( @ApplicationContext context: Context): BookSeatDatabase {
        return Room.databaseBuilder(
            context,
            BookSeatDatabase::class.java, DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideWorker( @ApplicationContext context: Context): WorkManager {
       return WorkManager.getInstance(context)
    }

    companion object{
        const val DB_NAME="BookSeatDB"
    }
}
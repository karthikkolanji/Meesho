package com.meesho.jakewarton.data.di

import android.content.Context
import androidx.room.Room
import com.meesho.jakewarton.data.db.BookSeatDao
import com.meesho.jakewarton.data.db.BookSeatDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class DataModule {

    @Provides
    @ViewModelScoped
    fun provideDao(bookSeatDatabase: BookSeatDatabase): BookSeatDao {
        return bookSeatDatabase.bookSeatDao()
    }

    @Provides
    @ViewModelScoped
    fun provideDB( @ApplicationContext context: Context): BookSeatDatabase {
        return Room.databaseBuilder(
            context,
            BookSeatDatabase::class.java, DB_NAME
        ).build()
    }

    companion object{
        const val DB_NAME="BookSeatDB"
    }
}
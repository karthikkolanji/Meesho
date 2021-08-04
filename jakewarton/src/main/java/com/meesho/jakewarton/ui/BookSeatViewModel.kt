package com.meesho.jakewarton.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.meesho.base.di.DispatcherProvider
import com.meesho.base.utils.State
import com.meesho.jakewarton.data.db.Repository
import com.meesho.jakewarton.domain.BookSeat
import com.meesho.jakewarton.domain.GetElapsedTime
import com.meesho.jakewarton.domain.GetSessionStatus
import com.meesho.jakewarton.domain.SubmitSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class BookSeatViewModel @Inject constructor(
    private val bookSeat: BookSeat,
    private val dispatcherProvider: DispatcherProvider,
    private val getElapsedTime: GetElapsedTime,
    private val getSessionStatus: GetSessionStatus,
    private val submitSession: SubmitSession,
    private val repository: Repository
) : ViewModel() {

    suspend fun deleteTable(){
        repository.deleteSession()
    }

    suspend fun bookSeat(scanResult: String) {
        bookSeat.bookSeat(scanResult)
    }

    suspend fun submit() = liveData {
        emit(State.LoadingState)
        try {
            emit(State.Success(submitSession.submit()))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(e)
        }
    }

    suspend fun getElapsedTime() = liveData {
        emit(State.LoadingState)
        try {
            getElapsedTime.get().collect {
                emit(State.Success(it))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(e)
        }
    }

    suspend fun getSessionStatus() = liveData {
        getSessionStatus.get().collect {
            emit(it)
        }
    }
}
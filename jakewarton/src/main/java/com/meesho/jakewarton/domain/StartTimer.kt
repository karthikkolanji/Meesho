package com.meesho.jakewarton.domain

import android.util.Log
import com.meesho.jakewarton.data.db.Repository
import com.meesho.jakewarton.utils.Utils
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StartTimer @Inject constructor(private val utils: Utils,
                                     private val repository: Repository,
) {

    suspend fun start() {
        val startTime = System.currentTimeMillis()

        while (true) {
            val elapsedTime = utils.millisecondToStandard(System.currentTimeMillis() - startTime)
            repository.updateTime(elapsedTime)
            Log.d("updateTime", "$elapsedTime ")
            delay(INTERVAL)
        }
    }

    companion object {
        const val INTERVAL = 1000L
    }
}
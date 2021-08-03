package com.meesho.jakewarton.domain

import com.meesho.jakewarton.data.db.Repository
import com.meesho.jakewarton.utils.Utils
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StartTimer @Inject constructor(
    private val utils: Utils,
    private val repository: Repository,
) {

    private val scope =
        MainScope()
    private var job: Job? = null
    suspend fun start() {
        val startTime = System.currentTimeMillis()
        job = scope.launch {
            while (true) {
                val elapsedTime =
                    utils.millisecondToStandard(System.currentTimeMillis() - startTime)
                repository.updateTime(elapsedTime)
                delay(INTERVAL)
            }
        }
    }

    fun stopTimer() {
        job?.cancel()
        job = null
    }

    companion object {
        const val INTERVAL = 1000L
    }
}
package com.meesho.jakewarton.domain

import android.util.Log
import com.meesho.jakewarton.utils.Utils
import kotlinx.coroutines.delay
import javax.inject.Inject

class StartTimer @Inject constructor(private val utils: Utils) {

    suspend fun start() {
        val startTime = System.currentTimeMillis()

            while (true) {
                val result=utils.millisecondToStandard(System.currentTimeMillis() - startTime)
                Log.d("StartTimer", "$result ")
                delay(1000)
        }
    }
}
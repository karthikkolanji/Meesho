package com.meesho.base.utils

import android.os.Handler
import android.os.Looper
import androidx.work.WorkManager
import com.meesho.base.BuildConfig
import timber.log.Timber
import java.util.*

object Utils{
    fun logWorkInfo(id: UUID) {
        if (BuildConfig.DEBUG) {
            val mainHandler = Handler(Looper.getMainLooper())

            val myRunnable =
                Runnable {
                    WorkManager.getInstance().getWorkInfoByIdLiveData(id).observeForever { it ->
                        it?.let { data ->
                            Timber.v(
                                "<<<<<Worker State:%s tags=%s runAttemptCount=%d",
                                data.state.toString(),
                                data.tags.toString(),
                                data.runAttemptCount
                            )
                        }
                    }
                }
            mainHandler.post(myRunnable)
        }
    }
}
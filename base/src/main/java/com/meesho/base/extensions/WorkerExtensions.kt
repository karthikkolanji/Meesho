package com.meesho.base.extensions

import androidx.work.OneTimeWorkRequest
import com.meesho.base.utils.Utils

fun OneTimeWorkRequest.enableWorkerLogging(): OneTimeWorkRequest {
    Utils.logWorkInfo(this.id)
    return this
}
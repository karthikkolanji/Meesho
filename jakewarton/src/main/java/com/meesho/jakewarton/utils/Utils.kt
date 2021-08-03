package com.meesho.jakewarton.utils

import com.meesho.jakewarton.data.entity.Timer
import org.apache.commons.text.StringEscapeUtils.unescapeJava
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class Utils @Inject constructor() {

    fun formatBarcodeResult(unformattedJson: String): String {
        val unQuotedString = unformattedJson.replace("^\"|\"$".toRegex(), "")
        return unescapeJava(unQuotedString)
    }

    fun millisecondToStandard(millis: Long): Timer {
        val day = TimeUnit.MILLISECONDS.toDays(millis).toInt()
        val hours = TimeUnit.MILLISECONDS.toHours(millis) - day * 24
        val minute =
            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.MILLISECONDS.toHours(millis) * 60
        val second =
            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MILLISECONDS.toMinutes(millis) * 60


        return Timer(hour = hours, minute = minute, seconds = second)
    }
}
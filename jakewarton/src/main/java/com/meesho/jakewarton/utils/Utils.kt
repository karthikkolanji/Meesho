package com.meesho.jakewarton.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.meesho.base.extensions.lessThanMarshMellow
import com.meesho.base.extensions.marshMellow
import com.meesho.jakewarton.data.entity.Timer
import org.apache.commons.text.StringEscapeUtils.unescapeJava
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class Utils @Inject constructor(private val context: Context) {

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

    fun isNetworkConnected(): Boolean {
        var connected = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        marshMellow {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            connected = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        }
        lessThanMarshMellow {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            connected = nwInfo.isConnected
        }
        return connected
    }
}
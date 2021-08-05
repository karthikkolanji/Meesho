package com.meesho.jakewarton.domain

import com.google.gson.Gson
import com.meesho.jakewarton.data.entity.QRScanResult
import org.apache.commons.text.StringEscapeUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ParseScanResult @Inject constructor(
) {

    fun parse(unformattedJson: String): QRScanResult {
        val unQuotedString = unformattedJson.replace("^\"|\"$".toRegex(), "")
        val unescapeString = StringEscapeUtils.unescapeJava(unQuotedString)
        return Gson().fromJson(unescapeString, QRScanResult::class.java)
    }
}
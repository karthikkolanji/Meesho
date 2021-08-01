package com.meesho.jakewarton.utils

import org.apache.commons.text.StringEscapeUtils.unescapeJava
import javax.inject.Inject

class Utils @Inject constructor(){

    fun formatBarcodeResult(unformattedJson:String):String{
        val unQuotedString = unformattedJson.replace("^\"|\"$".toRegex(), "")
        return unescapeJava(unQuotedString)
    }
}
package com.meesho.jakewarton.utils

import androidx.annotation.Keep

@Keep
class ScanError(error: String = "Please scan the same QR code to end session") : Exception(error)
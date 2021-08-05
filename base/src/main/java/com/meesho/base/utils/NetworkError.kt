package com.meesho.base.utils

import androidx.annotation.Keep

@Keep
class NetworkError(error: String = "Please check your internet connection") : Exception(error)
package com.meesho.base.extensions

import android.os.Build
import com.meesho.base.BuildConfig

inline fun nougat(block: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        block()
    }
}

inline fun oreo(block: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        block()
    }
}

inline fun marshMellow(block: () -> Unit) {
    if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
        block()
    }
}

inline fun lessThanMarshMellow(block: () -> Unit) {
    if ( Build.VERSION.SDK_INT < Build.VERSION_CODES.M ) {
        block()
    }
}

inline fun debug(block: () -> Unit) {
    if (BuildConfig.DEBUG) {
        block()
    }
}
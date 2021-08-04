package com.meesho.base.extensions

import android.os.Build

inline fun nougat(block: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        block()
    }
}
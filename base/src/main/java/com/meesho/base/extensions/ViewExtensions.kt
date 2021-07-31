package com.meesho.base.extensions

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.text.HtmlCompat
import com.google.android.material.snackbar.Snackbar

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}


fun View.snackbar(@StringRes stringId: Int, duration: Int): Snackbar =
    Snackbar.make(
        this,
        HtmlCompat.fromHtml(
            "<font color=\"#ffffff\">${context.getString(stringId)}</font>",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        ),
        duration

    )


fun View.setBooleanVisibility(value: Boolean) {
    if (value) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

fun View.enable() {
    isEnabled = true
}

fun View.disable() {
    isEnabled = false
}

fun AppCompatEditText.afterTextChange(afterTextChange: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChange(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

fun View.isVisible() = visibility == View.VISIBLE


fun TextView.setHtmlText(@StringRes stringId: Int) {
    this.text = HtmlCompat.fromHtml(context.getString(stringId), HtmlCompat.FROM_HTML_MODE_LEGACY)
}

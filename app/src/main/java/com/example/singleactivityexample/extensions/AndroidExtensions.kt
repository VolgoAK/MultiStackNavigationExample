package com.example.singleactivityexample.extensions

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

inline fun <reified T: Any> Activity.getExtraNotNull(key: String, default: T? = null): T {
    val value = intent?.extras?.get(key)
    return requireNotNull(if (value is T) value else default) { key }
}

inline fun <reified T: Any> Activity.getExtra(key: String, default: T? = null): T? {
    val value = intent?.extras?.get(key)
    return if(value is T) value else default
}

inline fun <reified T: Any> Activity.extra(key: String, default: T? = null) = lazy {
    getExtra(key, default)
}

inline fun <reified T: Any> Activity.extraNotNull(key: String, default: T? = null) = lazy {
    getExtraNotNull(key, default)
}

inline fun <reified T: Any> Fragment.getExtra(key: String, default: T? = null): T? {
    val value = arguments?.get(key)
    return if (value is T) value else default
}

inline fun <reified T: Any> Fragment.getExtraNotNull(key: String, default: T? = null): T {
    val value = arguments?.get(key)
    return requireNotNull(if (value is T) value else default) { key }
}

inline fun <reified T: Any> Fragment.extra(key: String, default: T? = null) = lazy {
    getExtra(key, default)
}
inline fun <reified T: Any> Fragment.extraNotNull(key: String, default: T? = null) = lazy {
    getExtraNotNull(key, default)
}

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.toast(@StringRes message: Int) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
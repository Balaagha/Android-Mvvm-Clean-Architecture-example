package com.example.androidmvvmcleanarchitectureexample.util.extentions

import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


fun Context.hideKeyboard(focusedView: View? = null) {
    val view = focusedView ?: (this as? AppCompatActivity
        ?: if (this is Fragment) this.activity else null)?.currentFocus

    if (view != null) {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
            view.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}

fun Context.convertDpToPixel(dp: Float): Int {
    return (dp * (resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
}

fun Context.convertDpToPixel(dp: Int): Int {
    return convertDpToPixel(dp.toFloat())
}

fun Context.pixelToDp(px: Int): Int {
    return pixelToDp(px.toFloat()).toInt()
}

fun Context.pixelToDp(px: Float): Float {
    return (px / resources.displayMetrics.density)
}

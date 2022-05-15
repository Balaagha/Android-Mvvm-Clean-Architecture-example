package com.example.common.utils.extentions

import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.os.Build
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import coil.load
import com.example.common.listeners.TextChangedListener
import com.example.common.utils.helper.isVersionHigherAndEqual
import com.google.android.material.textview.MaterialTextView


fun Window.setStatusBarColorAnyVersion(color: Int) {
    if (isVersionHigherAndEqual(Build.VERSION_CODES.M)) {
        this.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        this.statusBarColor = ContextCompat.getColor(this.context, color)
    } else {
        this.statusBarColor = ContextCompat.getColor(this.context, color)
    }
}

/**
 * This Kotlin extension function is responsible to add text changes more efficiently
 * @param afterTextChanged is lambda value which will invoke after changes happened
 */

// Text region


fun EditText.onTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextChangedListener {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            //not used
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            //not used
        }

        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s.toString())
        }
    })
}

/**
 * This Kotlin extension function is responsible to setLeftDrawable on EditText
 */
fun EditText.setLeftDrawable(
    @DrawableRes id: Int = 0,
    size: Int = 0,
    @ColorRes colorRes: Int = 0,
    padding: Int = 0
) {
    val drawable = ContextCompat.getDrawable(context, id)
    if (size != 0) {
        drawable?.setBounds(0, 0, size, size)
    }
    if (colorRes != 0) {
        val colorInt = ResourcesCompat.getColor(context.resources, colorRes, null)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable?.colorFilter = BlendModeColorFilter(colorInt, BlendMode.SRC_ATOP)
        } else {
            drawable?.setColorFilter(colorInt, PorterDuff.Mode.SRC_ATOP)
        }
    }
    this.compoundDrawablePadding = padding
    this.setCompoundDrawables(drawable, null, null, null)
}


/**
 * This method is responsible to reset all values of edittext
 */
fun EditText.resetText() {
    setText("")
}

fun MaterialTextView.setTextAppearanceAnyVersion(@StyleRes resId: Int?) {
    resId?.let {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)
            setTextAppearance(it)
        else
            setTextAppearance(context, it)
    }
}

fun TextView.setTextColorRes(@ColorRes resId: Int) {
    setTextColor(ContextCompat.getColor(context, resId))
}


// Image region

fun ImageView.setImageColorRes(@ColorRes resId: Int) {
    setColorFilter(
        ContextCompat.getColor(context, resId), android.graphics.PorterDuff.Mode.SRC_IN
    )
}


fun ImageView.loadImageFromUrl(
    url: String?,
    placeHolderImgRes: Int? = null,
    errorImgRes: Int? = null,
    isCrossFade: Boolean = false,
    crossFadeDuration: Int? = null
) {
    this.load(url){
        crossFadeDuration?.let {
            crossfade(crossFadeDuration)
        } ?: kotlin.run {
            crossfade(isCrossFade)
        }
        placeHolderImgRes?.let {
            placeholder(it)
        }
        errorImgRes?.let {
            this.error(it)
        }
    }
}





// View region

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.setVisibleOrInvisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
}

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun View.showKeyboard() {
    requestFocus()
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.also {
        it.showSoftInput(this, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }
}

/**
 * Animations
 */

/**
 * Set the visibility state of this view to [View.GONE] with fade out animation
 */
fun View.animateGone(duration: Long = 100, action: (() -> Unit)? = null) {
    if (visibility != View.GONE) {
        animate().setDuration(duration)
            .alpha(0f)
            .withEndAction {
                visibility = View.GONE
                action?.invoke()
            }
    }
}

/**
 * Set the visibility state of this view to [View.INVISIBLE] with fade out animation
 */
fun View.animateInvisible(duration: Long = 100) {
    if (visibility != View.INVISIBLE) {
        animate().setDuration(duration)
            .alpha(0f)
            .withEndAction {
                visibility = View.INVISIBLE
            }
    }
}

/**
 * Set the visibility state of this view to [View.VISIBLE] with fade in animation
 */
fun View.animateVisible(duration: Long = 100) {
    if (visibility != View.VISIBLE) {
        alpha = 0f
        visibility = View.VISIBLE
        animate().setDuration(duration)
            .alpha(1f)
    }
}

fun View.animateGoneLeftRight(duration: Long = 100, action: (() -> Unit)? = null) {
    if (visibility != View.GONE) {
        animate().setDuration(duration)
            .alpha(0f)
            .translationX(context.convertDpToPixel(20).toFloat())
            .withEndAction {
                action?.invoke()
            }
    }
}

fun View.animateVisibleRightToLeft(duration: Long = 100) {
    if (visibility != View.VISIBLE) {
        alpha = 0f
        translationX = context.convertDpToPixel(20).toFloat()
        visibility = View.VISIBLE
        animate().setDuration(duration)
            .alpha(1f).translationX(0f)
    }
}





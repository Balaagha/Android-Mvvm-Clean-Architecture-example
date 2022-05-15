package com.example.common.utils.extentions

import android.os.Build
import android.text.Editable
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.view.animation.Animation
import androidx.core.text.HtmlCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            removeObserver(this)
            observer.onChanged(t)
        }
    })
}

fun Animation.setOnAnimationEnd(function: () -> Unit) {
    this.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) {
            //not used
        }

        override fun onAnimationEnd(animation: Animation?) {
            function.invoke()
        }

        override fun onAnimationStart(animation: Animation?) {
            //not used
        }
    })
}

fun String.toEditable(): Editable = Editable.Factory
    .getInstance().newEditable(this)

@Suppress("DEPRECATION")
fun String?.convertHtmlTagToLink(htmlFlag: Int): Spanned {
    return when {
        this.isNullOrEmpty() -> {
            SpannableString("")
        }
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
            HtmlCompat.fromHtml(
                this.replace("\n", "<br />"),
                htmlFlag
            )
        }
        else -> {
            Html.fromHtml(this.replace("\n", "<br />"))
        }
    }
}

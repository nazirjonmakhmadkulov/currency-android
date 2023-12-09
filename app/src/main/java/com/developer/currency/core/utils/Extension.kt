package com.developer.currency.core.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.view.WindowInsets
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@SuppressLint("DiscouragedApi", "InternalInsetResource")
fun Activity.getStatusBarHeight(): Int {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        window.decorView.rootWindowInsets?.displayCutout?.let { return it.safeInsetTop }
    }
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0
}

fun Activity.getActionBarHeight(): Int {
    val ta: TypedArray = theme.obtainStyledAttributes(intArrayOf(android.R.attr.actionBarSize))
    return ta.getDimension(0, 0f).toInt()
}

@SuppressLint("DiscouragedApi", "InternalInsetResource")
fun Activity.getNavigationBarHeight(): Int {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val insets: WindowInsets = windowManager.currentWindowMetrics.windowInsets
        return insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
    }
    val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    return if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0
}

fun Context.getScreenWidth(): Int = this.resources.displayMetrics.widthPixels
fun Context.getScreenHeight(): Int = this.resources.displayMetrics.heightPixels

inline fun <T> Flow<T>.launchAndCollectIn(
    owner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline action: suspend CoroutineScope.(T) -> Unit
) = owner.lifecycleScope.launch {
    owner.repeatOnLifecycle(minActiveState) { collect { it -> action(it) } }
}
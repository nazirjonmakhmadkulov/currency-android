package com.developer.valyutaapp.utils

import android.app.Activity
import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowInsets
import android.widget.EditText
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.developer.valyutaapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun Activity.getStatusBarHeight(): Int {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        window.decorView.rootWindowInsets?.displayCutout?.let { return it.safeInsetTop }
    }
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return if (resourceId > 0) resources.getDimensionPixelSize(resourceId)
    else 0
}

fun Activity.getActionBarHeight(): Int {
    val ta: TypedArray = theme.obtainStyledAttributes(intArrayOf(R.attr.actionBarSize))
    return ta.getDimension(0, 0f).toInt()
}

fun Activity.getNavigationBarHeight(): Int {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val insets: WindowInsets = windowManager.currentWindowMetrics.windowInsets
        return insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
    }
    val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    return if (resourceId > 0) resources.getDimensionPixelSize(resourceId)
    else 0
}

fun Context.getScreenWidth(): Int =
    this.resources.displayMetrics.widthPixels - 10

fun Context.getScreenHeight(): Int =
    this.resources.displayMetrics.heightPixels

fun EditText.textChanges(): Flow<CharSequence?> {
    return callbackFlow {
        val listener = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                trySend(s)
            }
        }
        addTextChangedListener(listener)
        awaitClose { removeTextChangedListener(listener) }
    }.onStart { emit(text) }
}

fun LifecycleOwner.addRepeatingJob(
    state: Lifecycle.State,
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.() -> Unit
): Job = lifecycleScope.launch(coroutineContext) {
    repeatOnLifecycle(state, block)
}

inline fun <T> Flow<T>.launchAndCollectIn(
    owner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline action: suspend CoroutineScope.(T) -> Unit
) = owner.lifecycleScope.launch {
    owner.repeatOnLifecycle(minActiveState) {
        collect { action(it) }
    }
}
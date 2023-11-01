package com.developer.currency.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.developer.currency.ui.MainActivity
import com.developer.currency.utils.Utils.setStatusBar

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBar(window)
        splashAndroid12()
        postDelay()
    }

    private fun splashAndroid12() {
        val content = findViewById<View>(android.R.id.content)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            content.viewTreeObserver.addOnDrawListener { false }
        }
    }

    private fun postDelay() {
        Handler(Looper.getMainLooper()).postDelayed({ callMain() }, 1000)
    }

    private fun callMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
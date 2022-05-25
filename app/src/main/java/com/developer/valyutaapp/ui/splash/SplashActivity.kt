package com.developer.valyutaapp.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.annotation.RequiresApi
import com.developer.valyutaapp.core.common.PATH_EXP
import com.developer.valyutaapp.ui.MainViewModel
import com.developer.valyutaapp.ui.MainActivity
import com.developer.valyutaapp.utils.Utils
import com.developer.valyutaapp.utils.Utils.setStatusBar
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.KITKAT)
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
        Handler(Looper.getMainLooper()).postDelayed({
            callMain()
        }, 10)
    }

    private fun callMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
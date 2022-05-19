package com.developer.valyutaapp.ui.splash

import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import com.developer.valyutaapp.R
import android.widget.TextView
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.developer.valyutaapp.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashAndroid12()
        Handler(Looper.getMainLooper()).postDelayed({
            callMain()
        }, 2000)
    }

    private fun splashAndroid12() {
        val content = findViewById<View>(android.R.id.content)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            content.viewTreeObserver.addOnDrawListener { false }
        }
    }

    private fun callMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
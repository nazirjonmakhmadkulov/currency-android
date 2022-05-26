package com.developer.valyutaapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    @SuppressLint("SimpleDateFormat")
    val parser = SimpleDateFormat("yyyy-MM-dd")

    @SuppressLint("SimpleDateFormat")
    fun getDate(): String {
        return parser.format(Calendar.getInstance().time)
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateFormat(date: String): String {
        val formatter = SimpleDateFormat("dd.MM.yyyy")
        return formatter.format(parser.parse(date)!!)
    }

    @SuppressLint("SimpleDateFormat")
    fun getMonthAge(): String {
        val cal = Calendar.getInstance()
        cal.time = Date()
        cal.add(Calendar.MONTH, -1)
        return parser.format(cal.time)
    }

    @SuppressLint("SimpleDateFormat")
    fun isBetweenDate(date: String): String {
        val cal = Calendar.getInstance()
        cal.time = Date()
        cal.add(Calendar.MONTH, -1)

        return parser.format(cal.time)
    }

    fun mathNominal(a: Double, b: Double): Double {
        return a * b
    }

    fun mathValue(a: Double, b: Double, c: Double): Double {
        return a * c / b
    }

    fun Context.isNetworkAvailable(): Boolean {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
        //Internet connectivity check in Android Q
        val networks = connectivityManager.allNetworks
        var hasInternet = false
        if (networks.isNotEmpty()) {
            for (network in networks) {
                val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
                if (networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true)
                    hasInternet = true
            }
        }
        return hasInternet
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun setStatusBar(window: Window) {
        if (Build.VERSION.SDK_INT in 19..20) {
            setWindowFlag(window, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(window, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    private fun setWindowFlag(window: Window, bits: Int, on: Boolean) {
        val winParams = window.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        window.attributes = winParams
    }
}
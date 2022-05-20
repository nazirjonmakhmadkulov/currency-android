package com.developer.valyutaapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    @SuppressLint("SimpleDateFormat")
    fun getDate(): String {
        val dfDate: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dfDate.format(Calendar.getInstance().time)
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
}
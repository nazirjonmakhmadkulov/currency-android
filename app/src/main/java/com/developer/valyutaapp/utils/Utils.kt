package com.developer.valyutaapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    val date: String
        get() {
            val dfDate: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            return dfDate.format(Calendar.getInstance().time)
        }

    fun mathNominal(a: Double, b: Double): Double {
        return a * b
    }

    fun mathValue(a: Double, b: Double, c: Double): Double {
        return a * c / b
    }

    fun hasConnection(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (wifiInfo != null && wifiInfo.isConnected) {
            return true
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        if (wifiInfo != null && wifiInfo.isConnected) {
            return true
        }
        wifiInfo = cm.activeNetworkInfo
        return wifiInfo != null && wifiInfo.isConnected
    }
}
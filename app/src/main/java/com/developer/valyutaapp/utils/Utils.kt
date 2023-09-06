package com.developer.valyutaapp.utils

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.Window
import android.view.WindowManager
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@SuppressLint("SimpleDateFormat")
object Utils {
    private val parser = SimpleDateFormat("yyyy-MM-dd")

    fun getDate(): String {
        return parser.format(Calendar.getInstance().time)
    }

    fun getDateFormat(date: String): String {
        val formatter = SimpleDateFormat("dd.MM.yyyy")
        return formatter.format(parser.parse(date)!!)
    }

    fun getWeekAge(): String {
        val cal = Calendar.getInstance()
        cal.time = Date()
        cal.add(Calendar.DAY_OF_MONTH, -7)
        return parser.format(cal.time)
    }

    fun getMonthAge(): String {
        val cal = Calendar.getInstance()
        cal.time = Date()
        cal.add(Calendar.DAY_OF_MONTH, -30)
        return parser.format(cal.time)
    }

    fun getYearAge(): String {
        val cal = Calendar.getInstance()
        cal.time = Date()
        cal.add(Calendar.MONTH, -12)
        return parser.format(cal.time)
    }

    fun dateFormatDb(date: String): String {
        val formatter = SimpleDateFormat("dd.MM.yyyy")
        return parser.format(formatter.parse(date)!!)
    }

    fun dateFormatChart(date: String): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val parser = SimpleDateFormat("dd MMM")
        return parser.format(formatter.parse(date)!!)
    }

    fun decFormat(cost: Double): String {
        val dec = DecimalFormat("#.###")
        dec.roundingMode = RoundingMode.CEILING
        return dec.format(cost)
    }

    @Suppress("DEPRECATION")
    fun setStatusBar(window: Window) {
        setWindowFlag(window, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT
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

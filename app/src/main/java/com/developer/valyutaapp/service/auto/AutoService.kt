package com.developer.valyutaapp.service.auto

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import com.developer.valyutaapp.utils.Utils
import java.lang.Exception
import java.lang.UnsupportedOperationException
import java.util.*

class AutoService : Service() {
    var autoPresenter: AutoPresenter? = null
    private val mHandler = Handler()
    private var mTimer: Timer? = null
    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onCreate() {
        if (mTimer != null) mTimer!!.cancel() else mTimer = Timer()
        mTimer!!.scheduleAtFixedRate(TimeDisplay(), 0, notify.toLong())
        setupMVP()
        valuteList
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        mTimer!!.cancel()
    }

    private fun setupMVP() {
        autoPresenter = AutoPresenter(this.applicationContext)
    }

    private val valuteList: Unit
        get() {
            autoPresenter!!.valutesRemote()
        }

    internal inner class TimeDisplay : TimerTask() {
        override fun run() {
            // run on another thread
            mHandler.post {
                try {
                    if (Utils.hasConnection(this@AutoService)) {
                        setupMVP()
                        valuteList
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    companion object {
        const val notify = 300000
    }
}
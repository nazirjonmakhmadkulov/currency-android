package com.developer.valyutaapp.service.auto;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;


import com.developer.valyutaapp.utils.Utils;

import java.util.Timer;
import java.util.TimerTask;

public class AutoService extends Service {

    AutoPresenter autoPresenter;

    public static final int notify = 300000;

    private Handler mHandler = new Handler();
    private Timer mTimer = null;

    public AutoService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        if (mTimer != null)
            mTimer.cancel();
        else
            mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new TimeDisplay(), 0, notify);
        setupMVP();
        getValuteList();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
    }

    private void setupMVP() {
        autoPresenter = new AutoPresenter(this.getApplicationContext());
    }

    private void getValuteList() {
        autoPresenter.getValutesRemote();
    }

    class TimeDisplay extends TimerTask {
        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (Utils.hasConnection(AutoService.this)){
                            setupMVP();
                            getValuteList();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}

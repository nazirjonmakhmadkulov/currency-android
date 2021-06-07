package com.developer.valyutaapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class AutoService extends Service {
    public static final int notify = 300000;
    private Handler mHandler = new Handler();
    private Timer mTimer = null;

    public AutoService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        if (mTimer != null)
            mTimer.cancel();
        else
            mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new TimeDisplay(), 0, notify);

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
    protected boolean isOnline() {
        String cs = Context.CONNECTIVITY_SERVICE;
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(cs);
        if (cm.getActiveNetworkInfo() == null) {
            return false;
        } else {
            return true;
        }
    }

    class TimeDisplay extends TimerTask {
        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    // Toast.makeText(MyService.this, "Service is running", Toast.LENGTH_SHORT).show();
                    try {
                        //String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

                        if ( !isOnline() ){
                            Toast.makeText(AutoService.this, "Нет соединения с интернетом!",Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(AutoService.this, "Есть соединения с интернетом!",Toast.LENGTH_LONG).show();
                            //DBSimple.deleteRow(context);
                            //requestVygruzka = new RequestVygruzka(context);//Создаем объект для отправки данных
                            //requestVygruzka.execute();//Отправляем запрос
                            Toast.makeText(AutoService.this, "Обновлено данные", Toast.LENGTH_SHORT).show();

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }
}
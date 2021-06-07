package com.developer.valyutaapp;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class UpdateService extends Service {
    public static final int notify = 43200000;
    private Handler mHandler = new Handler();
    private Timer mTimer = null;
    DatabaseAdapter adapter;
    RequestVygruzka requestVygruzka;
    Context context;
    SQLiteDatabase database;
    public UpdateService() {
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
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        String datef = df.format(Calendar.getInstance().getTime());
                        Model getDates = adapter.getDataDate();
                        ModelDate modelDate = adapter.getDate();
                        boolean model = adapter.getDataNULL();
                        ContentValues cv = new ContentValues();
                        //String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                        if ( !isOnline() ){
                            Toast.makeText(context, "Нет соединения с интернетом!",Toast.LENGTH_LONG).show();
                        }else {
                            if (model == false) {
                                if (getDates.getDate().toString().equals(datef)) {
                                    DBSimple.deleteRow(context);
                                    Log.d("remove ", " = " +  DBSimple.deleteRow(context));
                                }
                                if (modelDate.get_dates().toString().equals(datef)){
                                    DBSimple.deleteDate(context);

                                }
                                Toast.makeText(context, "Есть соединения с интернетом!", Toast.LENGTH_LONG).show();
                                cv.put(DatabaseHelper.COLUMN_DATE, datef);
                                database.insert(DatabaseHelper.TABLE_DATE, null, cv);
                                requestVygruzka = new RequestVygruzka(context);//Получение данние
                                requestVygruzka.setResponseListener((RequestVygruzka.ResponseListener) UpdateService.this);
                                requestVygruzka.execute();
                                Toast.makeText(context, "Обновлено данные", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, "Есть соединения с интернетом!", Toast.LENGTH_LONG).show();
                                cv.put(DatabaseHelper.COLUMN_DATE, datef);
                                database.insert(DatabaseHelper.TABLE_DATE, null, cv);
                                requestVygruzka = new RequestVygruzka(context);//Получение данние
                                requestVygruzka.setResponseListener((RequestVygruzka.ResponseListener) UpdateService.this);
                                requestVygruzka.execute();
                                Toast.makeText(context, "Обновлено данные", Toast.LENGTH_SHORT).show();
                            }

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }
}
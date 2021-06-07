package com.developer.valyutaapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider {
    RequestVygruzka requestVygruzka;
    private PendingIntent pendingIntent;
    private static final String ACTION_UPDATE_CLICK = "UPDATE_CLICK";
   // private static final String ACTION_CLICK = "LIST_CLICK";
    SQLiteDatabase database;
    DatabaseHelper databaseHelper;
    DatabaseAdapter adapter;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        final AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        final Intent i = new Intent(context, MyService.class);


        if (pendingIntent == null) {
            pendingIntent = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
        }
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), 60000, pendingIntent);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
       // views.setOnClickPendingIntent(R.id.imageView, getPendingSelfIntent(context, ACTION_CLICK));
        views.setOnClickPendingIntent(R.id.updatewidget, getPendingUpdateIntent(context, ACTION_UPDATE_CLICK));
        appWidgetManager.updateAppWidget(appWidgetIds, views);
    }

//    protected PendingIntent getPendingSelfIntent(Context context, String action) {
//        Intent intent = new Intent(context, getClass());
//        intent.setAction(ACTION_CLICK);
//        return PendingIntent.getBroadcast(context, 0, intent, 0);
//    }
    protected PendingIntent getPendingUpdateIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(ACTION_UPDATE_CLICK);
        return PendingIntent.getBroadcast(context, 1, intent, 0);
    }
    protected boolean isOnline(Context context) {
        String cs = Context.CONNECTIVITY_SERVICE;
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(cs);
        if (cm.getActiveNetworkInfo() == null) {
            return false;
        } else {
            return true;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        adapter = new DatabaseAdapter(context);
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        Log.d("getAction", " = " + intent.getAction());
        if (ACTION_UPDATE_CLICK.equals(intent.getAction())) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String datef = df.format(java.util.Calendar.getInstance().getTime());
            Model getDates = adapter.getDataDate();
            ModelDate modelDate = adapter.getDate();
            boolean model = adapter.getDataNULL();
            ContentValues cv = new ContentValues();

            if ( !isOnline(context) ){
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
                    //requestVygruzka.setResponseListener(context);
                    requestVygruzka.execute();
                    Toast.makeText(context, "Обновлено данные", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Есть соединения с интернетом!", Toast.LENGTH_LONG).show();
                    cv.put(DatabaseHelper.COLUMN_DATE, datef);
                    database.insert(DatabaseHelper.TABLE_DATE, null, cv);
                    requestVygruzka = new RequestVygruzka(context);//Получение данние
                   // requestVygruzka.setResponseListener(this);
                    requestVygruzka.execute();
                    Toast.makeText(context, "Обновлено данные", Toast.LENGTH_SHORT).show();
                }

            }
        }

    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }
}


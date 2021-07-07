package com.developer.valyutaapp.service.widget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;


import com.developer.valyutaapp.R;

import io.paperdb.Paper;

public class WidgetService extends Service {

    private PendingIntent pendingIntent;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Paper.init(this);
        String charcode = Paper.book().read("charcode");
        String charcode2 = Paper.book().read("charcode2");
        String nominal = Paper.book().read("nominal");
        String value = Paper.book().read("value");
        String date = Paper.book().read("dat");
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        CharSequence widgetText = this.getString(R.string.appwidget_text);
        RemoteViews views = new RemoteViews(this.getPackageName(), R.layout.app_widget);
        views.setTextViewText(R.id.appwidget_textnominal, nominal);
        views.setTextViewText(R.id.appwidget_textnominaltj, value);
        views.setTextViewText(R.id.appwidget_textCode, charcode);
        views.setTextViewText(R.id.appwidget_textnominalCode, "TJS");
        Log.d("widget", " = " + value);

        views.setTextViewText(R.id.datet, "Курс НБТ  " + date);

        if (charcode == null || charcode.trim().isEmpty() || charcode.trim().length() == 0){
            Log.d("null" , "=" + charcode);
        }else {
            if (charcode.equals("USD")) {
                views.setImageViewResource(R.id.appwidget_image, R.drawable.america);
            }

            if (charcode2.equals("TJS")) {
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.tajikistan);
            }
        }
        ComponentName theWidget = new ComponentName(this, AppWidget.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        manager.updateAppWidget(theWidget, views);

        return super.onStartCommand(intent, flags, startId);
    }
}
package com.developer.valyutaapp;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.paperdb.Paper;

public class MyService extends Service {
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
            if (charcode.equals("EUR")) {
                views.setImageViewResource(R.id.appwidget_image, R.drawable.european);
            }
            if (charcode.equals("XDR")) {
                views.setImageViewResource(R.id.appwidget_image, R.mipmap.xdr);
            }
            if (charcode.equals("CNY")) {
                views.setImageViewResource(R.id.appwidget_image, R.drawable.china);
            }
            if (charcode.equals("CHF")) {
                views.setImageViewResource(R.id.appwidget_image, R.drawable.switzerland);
            }
            if (charcode.equals("RUB")) {
                views.setImageViewResource(R.id.appwidget_image, R.drawable.russia);
            }
            if (charcode.equals("UZS")) {
                views.setImageViewResource(R.id.appwidget_image, R.drawable.uzbekistan);
            }
            if (charcode.equals("KGS")) {//not
                views.setImageViewResource(R.id.appwidget_image, R.drawable.kyrgyzstan);
            }
            if (charcode.equals("KZT")) {
                views.setImageViewResource(R.id.appwidget_image, R.drawable.kazakhstan);
            }
            if (charcode.equals("BYN")) {
                views.setImageViewResource(R.id.appwidget_image, R.drawable.belarus);
            }
            if (charcode.equals("IRR")) {
                views.setImageViewResource(R.id.appwidget_image, R.drawable.iran);
            }
            if (charcode.equals("AFN")) {
                views.setImageViewResource(R.id.appwidget_image, R.drawable.afghanistan);
            }
            if (charcode.equals("PKR")) {
                views.setImageViewResource(R.id.appwidget_image, R.drawable.pakistan);
            }
            if (charcode.equals("TRY")) {
                views.setImageViewResource(R.id.appwidget_image, R.drawable.turkey);
            }
            if (charcode.equals("TMT")) {
                views.setImageViewResource(R.id.appwidget_image, R.drawable.turkmenistan);
            }
            if (charcode.equals("GBP")) {
                views.setImageViewResource(R.id.appwidget_image, R.drawable.georgia);
            }
            if (charcode.equals("AUD")) {
                views.setImageViewResource(R.id.appwidget_image, R.drawable.australia);
            }
            if (charcode.equals("DKK")) {
                views.setImageViewResource(R.id.appwidget_image, R.drawable.denmark);
            }
            if (charcode.equals("ISK")) {
                views.setImageViewResource(R.id.appwidget_image, R.drawable.iceland);
            }
            if (charcode.equals("CAD")) {
                views.setImageViewResource(R.id.appwidget_image, R.drawable.canada);
            }
            if (charcode.equals("KWD")) {
                views.setImageViewResource(R.id.appwidget_image, R.drawable.kuwait);
            }
            if (charcode.equals("NOK")) {
                views.setImageViewResource(R.id.appwidget_image, R.drawable.norway);
            }
            if (charcode.equals("SGD")) {
                views.setImageViewResource(R.id.appwidget_image, R.drawable.singapore);
            }
            if (charcode.equals("SEK")) {
                views.setImageViewResource(R.id.appwidget_image, R.drawable.sweden);
            }
            if (charcode.equals("JPY")) {
                views.setImageViewResource(R.id.appwidget_image, R.drawable.japan);
            }
            if (charcode.equals("AZN")) {
                views.setImageViewResource(R.id.appwidget_image, R.drawable.azerbaijan);
            }
            if (charcode.equals("AMD")) {//not
                views.setImageViewResource(R.id.appwidget_image, R.drawable.armenia);
            }
            if (charcode.equals("GEL")) {//not
                views.setImageViewResource(R.id.appwidget_image, R.drawable.georgia);
            }
            if (charcode.equals("MDL")) {
                views.setImageViewResource(R.id.appwidget_image, R.drawable.moldova);
            }
            if (charcode.equals("UAH")) {
                views.setImageViewResource(R.id.appwidget_image, R.drawable.ukraine);
            }
            if (charcode.equals("AED")) {
                views.setImageViewResource(R.id.appwidget_image, R.drawable.denmark);
            }
            if (charcode.equals("SAR")) {
                views.setImageViewResource(R.id.appwidget_image, R.drawable.saudi_arabi);
            }
            if (charcode.equals("INR")) {
                views.setImageViewResource(R.id.appwidget_image, R.drawable.india);
            }
            if (charcode.equals("PLN")) {
                views.setImageViewResource(R.id.appwidget_image, R.drawable.poland);
            }
            if (charcode.equals("MYR")) {
                views.setImageViewResource(R.id.appwidget_image, R.drawable.malaysia);
            }
            if (charcode.equals("THB")) {
                views.setImageViewResource(R.id.appwidget_image, R.drawable.thailand);
            }
            if (charcode.equals("TJS")) {
                views.setImageViewResource(R.id.appwidget_image, R.drawable.tajikistan);
            }



            if (charcode2.equals("USD")) {
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.america);
            }
            if (charcode2.equals("EUR")) {
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.european);
            }
            if (charcode2.equals("XDR")) {
                views.setImageViewResource(R.id.appwidget_imagetj, R.mipmap.xdr);
            }
            if (charcode2.equals("CNY")) {
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.china);
            }
            if (charcode2.equals("CHF")) {
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.switzerland);
            }
            if (charcode2.equals("RUB")) {
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.russia);
            }
            if (charcode2.equals("UZS")) {
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.uzbekistan);
            }
            if (charcode2.equals("KGS")) {//not
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.kyrgyzstan);
            }
            if (charcode2.equals("KZT")) {
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.kazakhstan);
            }
            if (charcode2.equals("BYN")) {
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.belarus);
            }
            if (charcode2.equals("IRR")) {
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.iran);
            }
            if (charcode2.equals("AFN")) {
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.afghanistan);
            }
            if (charcode2.equals("PKR")) {
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.pakistan);
            }
            if (charcode2.equals("TRY")) {
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.turkey);
            }
            if (charcode2.equals("TMT")) {
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.turkmenistan);
            }
            if (charcode2.equals("GBP")) {
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.georgia);
            }
            if (charcode2.equals("AUD")) {
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.australia);
            }
            if (charcode2.equals("DKK")) {
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.denmark);
            }
            if (charcode2.equals("ISK")) {
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.iceland);
            }
            if (charcode2.equals("CAD")) {
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.canada);
            }
            if (charcode2.equals("KWD")) {
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.kuwait);
            }
            if (charcode2.equals("NOK")) {
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.norway);
            }
            if (charcode2.equals("SGD")) {
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.singapore);
            }
            if (charcode2.equals("SEK")) {
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.sweden);
            }
            if (charcode2.equals("JPY")) {
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.japan);
            }
            if (charcode2.equals("AZN")) {
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.azerbaijan);
            }
            if (charcode2.equals("AMD")) {//not
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.armenia);
            }
            if (charcode2.equals("GEL")) {//not
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.georgia);
            }
            if (charcode2.equals("MDL")) {
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.moldova);
            }
            if (charcode2.equals("UAH")) {
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.ukraine);
            }
            if (charcode2.equals("AED")) {
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.denmark);
            }
            if (charcode2.equals("SAR")) {
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.saudi_arabi);
            }
            if (charcode2.equals("INR")) {
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.india);
            }
            if (charcode2.equals("PLN")) {
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.poland);
            }
            if (charcode2.equals("MYR")) {
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.malaysia);
            }
            if (charcode2.equals("THB")) {
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.thailand);
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
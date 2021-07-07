package com.developer.valyutaapp.service.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.widget.RemoteViews;

import com.developer.valyutaapp.R;

public class AppWidget extends AppWidgetProvider {

    private PendingIntent pendingIntent;
    private static final String ACTION_UPDATE_CLICK = "UPDATE_CLICK";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        final AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        final Intent i = new Intent(context, WidgetService.class);

        if (pendingIntent == null) {
            pendingIntent = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
        }
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), 60000, pendingIntent);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        views.setOnClickPendingIntent(R.id.updatewidget, getPendingUpdateIntent(context, ACTION_UPDATE_CLICK));
        appWidgetManager.updateAppWidget(appWidgetIds, views);
    }

    protected PendingIntent getPendingUpdateIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(ACTION_UPDATE_CLICK);
        return PendingIntent.getBroadcast(context, 1, intent, 0);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }
}

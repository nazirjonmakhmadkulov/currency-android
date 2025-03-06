package com.developer.currency.core.widget

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.widget.RemoteViews
import com.developer.currency.R

open class AppWidget : AppWidgetProvider() {
    private var pendingIntent: PendingIntent? = null
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, WidgetService::class.java)
        if (pendingIntent == null) {
            pendingIntent = PendingIntent.getService(
                context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }
        pendingIntent?.let {
            manager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), 60000, it)
        }
        val views = RemoteViews(context.packageName, R.layout.app_widget)
        views.setOnClickPendingIntent(R.id.updatewidget, getPendingUpdateIntent(context))
        appWidgetManager.updateAppWidget(appWidgetIds, views)
    }

    private fun getPendingUpdateIntent(context: Context): PendingIntent {
        val intent = Intent(context, javaClass)
        intent.action = ACTION_UPDATE_CLICK
        return PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_MUTABLE)
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
    }

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
    }

    companion object {
        private const val ACTION_UPDATE_CLICK = "UPDATE_CLICK"
    }
}
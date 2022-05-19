package com.developer.valyutaapp.service.widget

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.developer.valyutaapp.R
import android.widget.RemoteViews
import android.content.ComponentName
import android.appwidget.AppWidgetManager
import android.util.Log
import io.paperdb.Paper

class WidgetService : Service() {
    private var pendingIntent: PendingIntent? = null
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {}
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Paper.init(this)
        val charcode = Paper.book().read<String>("charcode")
        val charcode2 = Paper.book().read<String>("charcode2")
        val nominal = Paper.book().read<String>("nominal")
        val value = Paper.book().read<String>("value")
        val date = Paper.book().read<String>("dat")
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        val widgetText: CharSequence = this.getString(R.string.appwidget_text)
        val views = RemoteViews(this.packageName, R.layout.app_widget)
        views.setTextViewText(R.id.appwidget_textnominal, nominal)
        views.setTextViewText(R.id.appwidget_textnominaltj, value)
        views.setTextViewText(R.id.appwidget_textCode, charcode)
        views.setTextViewText(R.id.appwidget_textnominalCode, "TJS")
        Log.d("widget", " = $value")
        views.setTextViewText(R.id.datet, "Курс НБТ  $date")
        if (charcode == null || charcode.trim { it <= ' ' }
                .isEmpty() || charcode.trim { it <= ' ' }.isEmpty()) {
            Log.d("null", "=$charcode")
        } else {
            if (charcode == "USD") {
                views.setImageViewResource(R.id.appwidget_image, R.drawable.america)
            }
            if (charcode2 == "TJS") {
                views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.tajikistan)
            }
        }
        val theWidget = ComponentName(this, AppWidget::class.java)
        val manager = AppWidgetManager.getInstance(this)
        manager.updateAppWidget(theWidget, views)
        return super.onStartCommand(intent, flags, startId)
    }
}
package com.developer.currency.service.widget

import android.app.PendingIntent
import android.app.Service
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.IBinder
import android.widget.RemoteViews
import com.developer.currency.R
import io.paperdb.Paper
import timber.log.Timber

class WidgetService : Service() {
    private var pendingIntent: PendingIntent? = null
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {}

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Paper.init(this)
        val charCode = Paper.book().read<String>("charcode")
        val charCode2 = Paper.book().read<String>("charcode2")
        val nominal = Paper.book().read<String>("nominal")
        val value = Paper.book().read<String>("value")
        val date = Paper.book().read<String>("dat")

        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_MUTABLE)

        val views = RemoteViews(this.packageName, R.layout.app_widget)
        views.setTextViewText(R.id.appwidget_textnominal, nominal)
        views.setTextViewText(R.id.appwidget_textnominaltj, value)
        views.setTextViewText(R.id.appwidget_textCode, charCode)
        views.setTextViewText(R.id.appwidget_textnominalCode, "TJS")
        views.setTextViewText(R.id.datet, "Курс НБТ  $date")

        if (charCode == null || charCode.trim { it <= ' ' }.isEmpty() || charCode.trim { it <= ' ' }.isEmpty()) {
            Timber.tag("null").d("=%s", charCode)
        } else {
            if (charCode == "USD") views.setImageViewResource(R.id.appwidget_image, R.drawable.america)
            if (charCode2 == "TJS") views.setImageViewResource(R.id.appwidget_imagetj, R.drawable.tajikistan)
        }

        val theWidget = ComponentName(this, AppWidget::class.java)
        val manager = AppWidgetManager.getInstance(this)
        manager.updateAppWidget(theWidget, views)
        return super.onStartCommand(intent, flags, startId)
    }
}
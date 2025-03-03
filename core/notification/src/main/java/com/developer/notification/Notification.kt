package com.developer.notification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri

object Notification {
    private const val NOTIFICATION_CHANNEL_ID = "com.developer.valyutaapp"
    private const val NOTIFICATION_ID = 100
    private const val TARGET_ACTIVITY_NAME = "com.developer.currency.MainActivity"
    private const val DEEP_LINK_SCHEME_AND_HOST = "https://www.valyutaapp.developer.com"

    @SuppressLint("UnspecifiedImmutableFlag")
    fun showNotification(context: Context, title: String, message: String) {
        val notification: Notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(context.pendingIntent())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setWhen(System.currentTimeMillis())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentTitle(title).build()
            notification.flags = Notification.FLAG_AUTO_CANCEL
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                title,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
            notificationManager.notify(NOTIFICATION_ID, notification)
        } else {
            notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher)
                .setAutoCancel(true)
                .setContentText(message)
                .setContentIntent(context.pendingIntent())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentTitle(title).build()
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notification.flags = Notification.FLAG_AUTO_CANCEL
            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }

    private fun Context.pendingIntent(): PendingIntent? = PendingIntent.getActivity(
        this, 0, Intent().apply {
            action = Intent.ACTION_VIEW
            data = DEEP_LINK_SCHEME_AND_HOST.toUri()
            component = ComponentName(packageName, TARGET_ACTIVITY_NAME)
        },
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
    )
}
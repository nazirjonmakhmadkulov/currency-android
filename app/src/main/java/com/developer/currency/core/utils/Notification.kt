package com.developer.currency.core.utils

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.developer.currency.R
import com.developer.currency.ui.MainActivity

object Notification {
    private const val NOTIFICATION_CHANNEL_ID = "com.developer.valyutaapp"
    private const val NOTIFICATION_ID = 100

    @SuppressLint("UnspecifiedImmutableFlag")
    fun showNotification(context: Context, title: String, message: String) {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        val pi = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        }
        val notification: Notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pi)
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
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentText(message)
                .setContentIntent(pi)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentTitle(title).build()
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notification.flags = Notification.FLAG_AUTO_CANCEL
            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }
}
package com.udacity

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

private const val NOTIFICATION_ID = 0

fun NotificationManager.sendNotification( messageBody: String,
                                          applicationContext: Context,
                                          channelId: String,file: String,statue: String) {

    val contentIntent = Intent(applicationContext, DetailActivity::class.java)
    contentIntent.putExtra("file",file)
    contentIntent.putExtra("statue",statue)

    fun NotificationManager.cancelNotifications() {
        cancelAll()
    }

    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val builder = NotificationCompat.Builder(
        applicationContext,    channelId,
    )
        .setSmallIcon(R.drawable.ic_assistant_black_24dp)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(messageBody)
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)
        .addAction(
            R.drawable.ic_assistant_black_24dp,
            applicationContext.getString(R.string.notification_button),
            contentPendingIntent)

    notify(NOTIFICATION_ID, builder.build())


}
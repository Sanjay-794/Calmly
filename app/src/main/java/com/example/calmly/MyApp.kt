package com.example.calmly

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApp:Application() {
//    val channel = NotificationChannel(
//        "media_channel_id",
//        "Media Playback",
//        NotificationManager.IMPORTANCE_LOW
//    )
//    val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//    manager.createNotificationChannel(channel)
}
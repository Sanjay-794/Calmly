//package com.example.calmly.util
//
//import android.app.Notification
//import android.app.PendingIntent
//import android.content.Context
//import android.content.Intent
//import android.graphics.BitmapFactory
//import android.os.Build
//import androidx.core.app.NotificationCompat
//import android.support.v4.media.session.MediaSessionCompat
//import android.widget.RemoteViews
//import com.example.calmly.NotificationChannels
//import com.example.calmly.service.MediaPlaybackService
//import com.example.calmly.R
//
//object NotificationUtils {
//
//    fun createMediaNotification(
//        context: Context,
//        mediaSession: MediaSessionCompat,
//        isPlaying: Boolean,
//        title: String,
//        imageResId: Int? = null,
//        currentPosition: Int = 0,
//        duration: Int = 0
//    ): Notification {
//
//
//        val playPauseAction = if (isPlaying) {
//            NotificationCompat.Action.Builder(
//                R.drawable.baseline_pause_24, "Pause",
//                getPendingIntent(context, MediaPlaybackService.ACTION_PAUSE)
//            ).build()
//        } else {
//            NotificationCompat.Action.Builder(
//                R.drawable.baseline_play_arrow_24, "Play",
//                getPendingIntent(context, MediaPlaybackService.ACTION_PLAY)
//            ).build()
//        }
//
//        val stopAction = NotificationCompat.Action.Builder(
//            R.drawable.baseline_stop_24, "Stop",
//            getPendingIntent(context, MediaPlaybackService.ACTION_STOP)
//        ).build()
//
//        val largeIcon = imageResId?.let {
//            BitmapFactory.decodeResource(context.resources, it)
//        }
//
//        val progress = if (duration > 0) {
//            (100 * currentPosition) / duration
//        } else {
//            0
//        }
//
//        return NotificationCompat.Builder(context, NotificationChannels.MEDIA_CHANNEL_ID)
//            .setContentTitle(title)
//            .setContentText(if (isPlaying) "Playing..." else "Paused")
//            .setSmallIcon(R.drawable.app_logo)
//            .setLargeIcon(largeIcon)
//            .setOnlyAlertOnce(true)
//            .setPriority(NotificationCompat.PRIORITY_LOW)
//            .setOngoing(isPlaying)
//            .setStyle(
//                androidx.media.app.NotificationCompat.MediaStyle()
//                    .setMediaSession(mediaSession.sessionToken)
//                    .setShowActionsInCompactView(0, 1)
//            )
//            .addAction(playPauseAction)
//            .addAction(stopAction)
//            .setProgress(100, progress, false) // Visual progress bar
//            .build()
//    }
//
//    private fun getPendingIntent(context: Context, action: String): PendingIntent {
//        val intent = Intent(context, MediaPlaybackService::class.java).apply {
//            this.action = action
//        }
//        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//        else PendingIntent.FLAG_UPDATE_CURRENT
//
//        return PendingIntent.getService(context, action.hashCode(), intent, flags)
//    }
//}
package com.example.calmly.util

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import android.support.v4.media.session.MediaSessionCompat
import com.example.calmly.service.MediaPlaybackService
import com.example.calmly.R

object NotificationUtils {

    fun createMediaNotification(
        context: Context,
        mediaSession: MediaSessionCompat,
        isPlaying: Boolean
    ) = NotificationCompat.Builder(context, "media_channel_id")
        .setSmallIcon(R.drawable.app_logo)
        .setContentTitle("Calmly - Playing Sounds")
        .setContentText("Tap to control playback")
        .setStyle(
            androidx.media.app.NotificationCompat.MediaStyle()
            .setMediaSession(mediaSession.sessionToken)
            .setShowActionsInCompactView(0, 1, 2))
        .addAction(
            if (isPlaying) R.drawable.baseline_pause_24 else R.drawable.baseline_play_arrow_24,
            if (isPlaying) "Pause" else "Play",
            getPendingIntent(
                context,
                if (isPlaying) MediaPlaybackService.ACTION_PAUSE else MediaPlaybackService.ACTION_PLAY
            )
        )
        .addAction(
            R.drawable.baseline_stop_24,
            "Stop",
            getPendingIntent(context, MediaPlaybackService.ACTION_STOP)
        )
        .setOngoing(isPlaying)
        .setPriority(NotificationCompat.PRIORITY_LOW)
        .build()

    private fun getPendingIntent(context: Context, action: String): PendingIntent {
        val intent = Intent(context, MediaPlaybackService::class.java).apply {
            this.action = action
        }
        return PendingIntent.getService(
            context,
            0,  // same requestCode for all actions to avoid stacking
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}
package com.example.calmly.service

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.transition.Transition
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.ui.PlayerNotificationManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.example.calmly.MainActivity
import com.example.calmly.NotificationChannels
import com.example.calmly.R
//import com.example.calmly.SoundData
import com.example.calmly.media.MediaPlayerManager
import com.example.calmly.model.SoundItem


@UnstableApi
class MediaPlaybackService : MediaSessionService() {

    private lateinit var mediaSession: MediaSession
    private lateinit var mediaPlayerManager: MediaPlayerManager
    private lateinit var player: ExoPlayer
    private var playerNotificationManager: PlayerNotificationManager? = null

    override fun onCreate() {
        super.onCreate()
        mediaPlayerManager = MediaPlayerManager(this)
        player = mediaPlayerManager.getOrCreatePlayer()

        mediaSession = MediaSession.Builder(this, player)
            .setId("CalmlySession")
            .build()

        setupNotification()
    }

    private fun setupNotification() {
        playerNotificationManager = PlayerNotificationManager.Builder(
            this,
            NOTIFICATION_ID,
            NotificationChannels.MEDIA_CHANNEL_ID
        )
            .setChannelNameResourceId(R.string.notification_channel_name)
            .setChannelDescriptionResourceId(R.string.notification_channel_description)
            .setMediaDescriptionAdapter(object : PlayerNotificationManager.MediaDescriptionAdapter {
                override fun getCurrentContentTitle(player: Player): CharSequence {
                    return mediaPlayerManager.getCurrentSound()?.title ?: "Calmly"
                }

                override fun createCurrentContentIntent(player: Player): PendingIntent? {
                    val intent = Intent(this@MediaPlaybackService, MainActivity::class.java)
                    return PendingIntent.getActivity(
                        this@MediaPlaybackService, 0, intent,
                        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                    )
                }

                override fun getCurrentContentText(player: Player): CharSequence? = null

override fun getCurrentLargeIcon(
    player: Player,
    callback: PlayerNotificationManager.BitmapCallback
): Bitmap? {
    val imageUrl = mediaPlayerManager.getCurrentSound()?.image ?: return null

    Glide.with(this@MediaPlaybackService)
        .asBitmap()
        .load(imageUrl)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?) {
                callback.onBitmap(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })

    return null
}
            })
            .setSmallIconResourceId(R.drawable.app_logo)
            .setNotificationListener(object : PlayerNotificationManager.NotificationListener {
                override fun onNotificationPosted(
                    notificationId: Int,
                    notification: android.app.Notification,
                    ongoing: Boolean
                ) {
                    if (ongoing) {
                        startForeground(notificationId, notification)
                    }
                }

                override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
                    if (dismissedByUser) stopSelf()
                }
            })
            .build()
            .also { manager ->
                manager.setPlayer(player)
//                if (platformToken != null) {
                    manager.setMediaSessionToken(mediaSession.platformToken)
//                } // ✅ Fix
                manager.setUseNextAction(false)
                manager.setUsePreviousAction(false)
                manager.setUseFastForwardAction(false)
                manager.setUseRewindAction(false)
                manager.setUseChronometer(true)
                manager.setUsePlayPauseActions(true)
                manager.setUseStopAction(false)

            }
    }

    @SuppressLint("MissingSuperCall")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_PLAY -> {
                val title = intent.getStringExtra(EXTRA_SOUND_TITLE) ?: return START_NOT_STICKY
                val url = intent.getStringExtra(EXTRA_SOUND_URL) ?: return START_NOT_STICKY
                val image = intent.getStringExtra(EXTRA_SOUND_IMAGE) ?: ""

                val sound = SoundItem(title = title, url = url, image = image)
                mediaPlayerManager.playSound(sound)
            }

            ACTION_PAUSE -> mediaPlayerManager.pause()
            ACTION_STOP -> {
                mediaPlayerManager.stop()
                stopSelf()
                return START_NOT_STICKY
            }
        }

        return START_STICKY
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession {
        return mediaSession
    }

    override fun onDestroy() {
        super.onDestroy()
        playerNotificationManager?.setPlayer(null)
        mediaSession.release()
//        compatSession.release()
        mediaPlayerManager.release()
    }

    companion object {
        const val NOTIFICATION_ID = 101

        const val ACTION_PLAY = "com.example.calmly.ACTION_PLAY"
        const val ACTION_PAUSE = "com.example.calmly.ACTION_PAUSE"
        const val ACTION_STOP = "com.example.calmly.ACTION_STOP"
        const val ACTION_PLAY_RESOURCE = "com.example.calmly.ACTION_PLAY_RESOURCE"
        const val EXTRA_RES_ID = "res_id"
        const val EXTRA_SOUND_TITLE = "extra_sound_title"
        const val EXTRA_SOUND_URL = "extra_sound_url"
        const val EXTRA_SOUND_IMAGE = "extra_sound_image"
    }
}
package com.example.calmly.service

import android.app.Service
import android.content.Intent
import android.media.session.PlaybackState
import android.os.IBinder
import android.support.v4.media.session.MediaSessionCompat
import com.example.calmly.media.MediaPlayerManager
import com.example.calmly.util.NotificationUtils
import com.example.calmly.viewmodel.PlaybackUiState

class MediaPlaybackService : Service() {

    private lateinit var mediaSession: MediaSessionCompat

    private lateinit var mediaPlayerManager: MediaPlayerManager

    override fun onCreate() {
        super.onCreate()
        mediaSession = MediaSessionCompat(this, "Calmly")
        mediaPlayerManager = MediaPlayerManager(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action

        when (action) {
            ACTION_PLAY_RESOURCE -> {
                val resId = intent.getIntExtra(EXTRA_RES_ID, -1)
                if (resId != -1) {
                    mediaPlayerManager.playSoundFromRaw(resId)
                    updateNotification(isPlaying = true)
                }
//                broadcastPlaybackState(com.example.calmly.viewmodel.PlaybackState.PLAYING.toString())
            }

            ACTION_PLAY -> {
                mediaPlayerManager.resume()
                updateNotification(isPlaying = true)
//                broadcastPlaybackState(com.example.calmly.viewmodel.PlaybackState.PLAYING.toString())
            }

            ACTION_PAUSE -> {
                mediaPlayerManager.pause()
                updateNotification(isPlaying = false)
//                broadcastPlaybackState(com.example.calmly.viewmodel.PlaybackState.PAUSED.toString())
            }

            ACTION_STOP -> {
                mediaPlayerManager.stop()
                stopForeground(STOP_FOREGROUND_REMOVE)
                stopSelf()
//                broadcastPlaybackState(com.example.calmly.viewmodel.PlaybackState.STOPPED.toString())
                return START_NOT_STICKY
            }

            else -> {
                updateNotification(isPlaying = mediaPlayerManager.isPlaying())
            }
        }

        return START_STICKY
    }


    override fun onDestroy() {
        mediaPlayerManager.release()
        mediaSession.release()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)

        // Stop playback via your manager
        mediaPlayerManager.stop()

        // Remove notification
        stopForeground(true)

        // Stop the service
        stopSelf()
    }

    companion object {
        const val ACTION_PLAY = "com.example.calmly.ACTION_PLAY"
        const val ACTION_PAUSE = "com.example.calmly.ACTION_PAUSE"
        const val ACTION_STOP = "com.example.calmly.ACTION_STOP"
        const val ACTION_PLAY_RESOURCE = "com.example.calmly.ACTION_PLAY_RESOURCE"
        const val EXTRA_RES_ID = "res_id"

        const val ACTION_PLAYBACK_STATE_CHANGED = "com.example.calmly.PLAYBACK_STATE_CHANGED"
        const val EXTRA_PLAYBACK_STATE = "playback_state"

        const val NOTIFICATION_ID = 101
    }

    private fun updateNotification(isPlaying: Boolean) {
        val notification = NotificationUtils.createMediaNotification(
            context = this,
            mediaSession = mediaSession,
            isPlaying = isPlaying
        )
        startForeground(NOTIFICATION_ID, notification)
    }

}

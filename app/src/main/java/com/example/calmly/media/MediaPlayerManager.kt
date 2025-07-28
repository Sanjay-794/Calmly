package com.example.calmly.media

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

class MediaPlayerManager(private val context: Context)
{
    private var exoPlayer: ExoPlayer? = null

    fun initializePlayer() {
        if (exoPlayer == null) {
            exoPlayer = ExoPlayer.Builder(context).build()
        }
    }

    fun playSoundFromRaw(rawResId: Int) {
        // Stop and clear previous playback before starting new one
        if (exoPlayer?.isPlaying == true) {
            exoPlayer?.stop()

            exoPlayer?.clearMediaItems()
        }

        initializePlayer()

        val uri = Uri.parse("android.resource://${context.packageName}/$rawResId")
        val mediaItem = MediaItem.fromUri(uri)
        Log.d("MediaPlayerManager", "Playing sound from raw: $rawResId")

        exoPlayer?.setMediaItem(mediaItem)
        exoPlayer?.prepare()
        exoPlayer?.playWhenReady = true
    }

    fun pause() {
        exoPlayer?.pause()
    }

    fun resume() {
        exoPlayer?.play()
    }

    fun stop() {
        exoPlayer?.stop()
    }

    fun isPlaying(): Boolean {
        return exoPlayer?.isPlaying == true
    }

    fun release() {
        exoPlayer?.release()
        exoPlayer = null
    }

    fun getPlayer(): ExoPlayer? = exoPlayer
}
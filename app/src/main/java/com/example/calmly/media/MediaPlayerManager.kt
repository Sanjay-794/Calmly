package com.example.calmly.media

import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.calmly.model.SoundItem



class MediaPlayerManager(private val context: Context) {
    private var exoPlayer: ExoPlayer? = null
    private var currentSound: SoundItem? = null

    fun getOrCreatePlayer(): ExoPlayer {
        if (exoPlayer == null) {
            exoPlayer = ExoPlayer.Builder(context).build()
        }
        return exoPlayer!!
    }

    fun playSound(sound: SoundItem) {
        val player = getOrCreatePlayer()
        if (player.isPlaying) {
            player.stop()
            player.clearMediaItems()
        }

        val uri = Uri.parse("android.resource://${context.packageName}/${sound.soundResId}")
        val mediaItem = MediaItem.fromUri(uri)

        player.setMediaItem(mediaItem)
        player.prepare()
        player.playWhenReady = true
        player.repeatMode = ExoPlayer.REPEAT_MODE_ONE

        currentSound = sound
    }

    fun pause() = exoPlayer?.pause()

    fun resume() = exoPlayer?.play()

    fun stop() {
        exoPlayer?.stop()
        currentSound = null
    }

    fun isPlaying(): Boolean = exoPlayer?.isPlaying == true

    fun getCurrentSound(): SoundItem? = currentSound

    fun release() {
        exoPlayer?.release()
        exoPlayer = null
        currentSound = null
    }
}
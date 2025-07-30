package com.example.calmly.viewmodel

import android.content.Context
import android.content.Intent
import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.UnstableApi
import com.example.calmly.SoundData
import com.example.calmly.service.MediaPlaybackService
import com.example.calmly.media.MediaPlayerManager
import com.example.calmly.model.SoundItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaViewModel@Inject constructor(private val mediaPlayerManager: MediaPlayerManager) : ViewModel()
{
    private val _playbackUiState = MutableStateFlow(PlaybackUiState())
    val playbackUiState = _playbackUiState.asStateFlow()

    @OptIn(UnstableApi::class)
    fun onSoundCardClicked(context: Context, sound: SoundItem) {
        val currentState = _playbackUiState.value

        when {
            currentState.currentPlaying?.soundResId != sound.soundResId -> {
                val intent = Intent(context, MediaPlaybackService::class.java).apply {
                    action = MediaPlaybackService.ACTION_PLAY_RESOURCE
                    putExtra(MediaPlaybackService.EXTRA_RES_ID, sound.soundResId)
                }
                context.startForegroundService(intent)

                _playbackUiState.value = PlaybackUiState(sound, PlaybackState.PLAYING)
            }

            currentState.state == PlaybackState.PLAYING -> {
                val intent = Intent(context, MediaPlaybackService::class.java).apply {
                    action = MediaPlaybackService.ACTION_PAUSE
                }
                context.startService(intent)

                _playbackUiState.update { it.copy(state = PlaybackState.PAUSED) }
            }

            currentState.state == PlaybackState.PAUSED -> {
                val intent = Intent(context, MediaPlaybackService::class.java).apply {
                    action = MediaPlaybackService.ACTION_STOP
                }
                context.startService(intent)

                _playbackUiState.value = PlaybackUiState(null, PlaybackState.STOPPED)
            }
        }
    }

    fun updatePlaybackState(state: PlaybackState) {
    _playbackUiState.update { it.copy(state = state) }
}

    fun stopPlayback() {
        _playbackUiState.update {
            it.copy(state = PlaybackState.STOPPED, currentPlaying = null)
        }
    }
}

enum class PlaybackState {
    STOPPED, PLAYING, PAUSED
}

data class PlaybackUiState(
    val currentPlaying: SoundItem? = null,
    val state: PlaybackState = PlaybackState.STOPPED
)




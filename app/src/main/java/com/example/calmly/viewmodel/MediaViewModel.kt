package com.example.calmly.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.calmly.service.MediaPlaybackService
import com.example.calmly.media.MediaPlayerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MediaViewModel@Inject constructor(private val mediaPlayerManager: MediaPlayerManager) : ViewModel()
{
    private val _playbackUiState = MutableStateFlow(PlaybackUiState())
    val playbackUiState = _playbackUiState.asStateFlow()

    fun onSoundCardClicked(context: Context, resId: Int) {
        val currentState = _playbackUiState.value

        when {
            currentState.currentPlayingResId != resId -> {
                val intent = Intent(context, MediaPlaybackService::class.java).apply {
                    action = MediaPlaybackService.ACTION_PLAY_RESOURCE
                    putExtra(MediaPlaybackService.EXTRA_RES_ID, resId)
                }
                context.startForegroundService(intent)

                _playbackUiState.value = PlaybackUiState(resId, PlaybackState.PLAYING)
            }

            currentState.state == PlaybackState.PLAYING -> {
                val intent = Intent(context, MediaPlaybackService::class.java).apply {
                    action = MediaPlaybackService.ACTION_PAUSE
                }
                context.startService(intent)

                _playbackUiState.value = currentState.copy(state = PlaybackState.PAUSED)
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
            it.copy(state = PlaybackState.STOPPED, currentPlayingResId = null)
        }
    }

}

enum class PlaybackState {
    STOPPED, PLAYING, PAUSED
}

data class PlaybackUiState(
    val currentPlayingResId: Int? = null,
    val state: PlaybackState = PlaybackState.STOPPED
)

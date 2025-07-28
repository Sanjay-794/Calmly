package com.example.calmly.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calmly.R
import com.example.calmly.viewmodel.MediaViewModel
import com.example.calmly.viewmodel.PlaybackState
import com.example.calmly.model.SoundItem

@Composable
fun SoundCard(item: SoundItem,
              viewModel: MediaViewModel
)
{

    val context= LocalContext.current
    val playbackUiState by viewModel.playbackUiState.collectAsState()
    val isThisCardActive = playbackUiState.currentPlayingResId == item.soundResId
    val playbackState = playbackUiState.state

    Box(
        modifier = Modifier
            .height(240.dp)
            .padding(6.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable {
                viewModel.onSoundCardClicked(context, item.soundResId)
            }
    ) {
        Image(
            painter = painterResource(id = item.imageRes),
            contentDescription = item.title,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )

        // Top-right timestamp
        Text(
            text = item.duration+"min",
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(6.dp)
                .background(Color.Black.copy(alpha = 0.5f), shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 5.dp, vertical = 2.dp)
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.4f))
                .padding(6.dp)
        ) {
            Text(
                text = item.title,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.CenterStart)
            )
        }

        // Apply grey overlay when this card is active
        if (isThisCardActive && playbackState != PlaybackState.STOPPED) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Black.copy(alpha = 0.4f))
            )

            // Show play/pause icon in center
            val icon = when (playbackState) {
                PlaybackState.PLAYING -> R.drawable.baseline_pause_24
                PlaybackState.PAUSED -> R.drawable.baseline_play_arrow_24
                else -> null
            }

            icon?.let {
                Icon(
                    painter = painterResource(it),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(48.dp)
                )
            }
        }
    }

}
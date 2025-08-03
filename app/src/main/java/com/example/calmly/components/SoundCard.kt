package com.example.calmly.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.calmly.R
import com.example.calmly.model.SoundItem
import com.example.calmly.viewmodel.MediaViewModel
import com.example.calmly.viewmodel.PlaybackState


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SoundCard(
    item: SoundItem,
    viewModel: MediaViewModel
) {
    val context = LocalContext.current
    val playbackUiState by viewModel.playbackUiState.collectAsState()

    val isThisCardActive = playbackUiState.currentPlaying?.url == item.url
    val playbackState = playbackUiState.state

    Box(
        modifier = Modifier
            .height(240.dp)
            .padding(6.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable {
                viewModel.onSoundCardClicked(context, item)
            }
    ) {

        GlideImage(model = item.image,
            contentDescription = "image of song",
            contentScale = Crop,
            modifier = Modifier.fillMaxHeight().fillMaxWidth())

        // Bottom overlay with title
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

//         Grey overlay & icon when active
        if (isThisCardActive && playbackState != PlaybackState.STOPPED) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Black.copy(alpha = 0.4f))
            )

            val icon = when (playbackState) {
                PlaybackState.PLAYING -> R.drawable.baseline_pause_24
                PlaybackState.PAUSED -> R.drawable.baseline_play_arrow_24
                else -> null
            }

            if (icon != null) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(48.dp)
                )
            }
        }
    }}

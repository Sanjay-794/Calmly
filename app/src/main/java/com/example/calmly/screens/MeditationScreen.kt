package com.example.calmly.screens

import android.content.Context
import android.media.MediaMetadataRetriever
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.calmly.viewmodel.MediaViewModel
import com.example.calmly.R
import com.example.calmly.SoundData
import com.example.calmly.components.SoundCard
import com.example.calmly.model.SoundItem

@Composable
fun MeditationScreen() {
    val context = LocalContext.current
    val mediaViewModel: MediaViewModel = hiltViewModel()

    val items=SoundData.getMeditationSounds(context)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(items) { soundItem ->
                SoundCard(item = soundItem, mediaViewModel)
            }
        }
    }
}
//}

fun getRawAudioDurationInMinutes(context: Context, rawResId: Int): Double {
    val retriever = MediaMetadataRetriever()
    val assetFileDescriptor = context.resources.openRawResourceFd(rawResId)

    retriever.setDataSource(
        assetFileDescriptor.fileDescriptor,
        assetFileDescriptor.startOffset,
        assetFileDescriptor.length
    )

    val durationStr = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
    retriever.release()

    val durationMs = durationStr?.toLongOrNull() ?: 0L
    val minutes = durationMs / 60000.0
    return String.format("%.1f", minutes).toDouble()  // round to 1 decimal
}
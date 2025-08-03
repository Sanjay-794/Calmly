package com.example.calmly.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.calmly.viewmodel.MediaViewModel
import com.example.calmly.components.SoundCard
@Composable
fun SleepScreen() {
    val context = LocalContext.current
    val mediaViewModel: MediaViewModel = hiltViewModel()

//    val items = SoundData.getSleepSounds(context)
    val items by mediaViewModel.songs.collectAsState()
    val limitedItems = items.takeLast(5)

    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(limitedItems) { soundItem ->
                SoundCard(item = soundItem, mediaViewModel)
            }
        }
    }
}
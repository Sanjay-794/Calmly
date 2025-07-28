package com.example.calmly.screens

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
import com.example.calmly.components.SoundCard
import com.example.calmly.model.SoundItem

//@Preview(showBackground = true, showSystemUi = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SleepScreen()
{

    val context= LocalContext.current

    val items = listOf(
        SoundItem("Beautiful Dream", getRawAudioDurationInMinutes(context,R.raw.beautiful_dream).toString(), R.drawable.sleep1, R.raw.beautiful_dream),
        SoundItem("chill Bro", getRawAudioDurationInMinutes(context,R.raw.chill_bro).toString(), R.drawable.sleep2, R.raw.chill_bro),
        SoundItem("close the lights", getRawAudioDurationInMinutes(context,R.raw.close_the_lights).toString(), R.drawable.sleep3, R.raw.close_the_lights),
        SoundItem("don't forget me", getRawAudioDurationInMinutes(context,R.raw.dont_forget_me).toString(), R.drawable.sleep4, R.raw.dont_forget_me),
        SoundItem("loving you is easy", getRawAudioDurationInMinutes(context,R.raw.loving_you_is_easy).toString(), R.drawable.sleep5, R.raw.loving_you_is_easy),
        SoundItem("remember me", getRawAudioDurationInMinutes(context,R.raw.remember_me).toString(), R.drawable.sleep3, R.raw.remember_me)
        // Add more
    )

    val mediaViewModel: MediaViewModel = hiltViewModel()

    Box(
        modifier = Modifier
            .fillMaxSize()
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
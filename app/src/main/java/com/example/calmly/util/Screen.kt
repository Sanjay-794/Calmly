package com.example.calmly.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.calmly.R

sealed class Screen(val route: String, val label: String, val icon: Int) {
    object Meditation : Screen("meditation", "Meditation", R.drawable.meditation_icon)
    object Sleep : Screen("sleep", "Sleep", R.drawable.sleep_icon)
}
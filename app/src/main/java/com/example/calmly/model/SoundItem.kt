package com.example.calmly.model

data class SoundItem(
    val title: String,
    val duration: String, // e.g. "5 min"
    val imageRes: Int,
    val soundResId: Int
)

package com.example.calmly

import android.util.Log
import com.example.calmly.model.SoundItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SongRepository @Inject constructor(
    private val database: FirebaseDatabase
) {
    private val _songsFlow = MutableStateFlow<List<SoundItem>>(emptyList())
    val songsFlow: StateFlow<List<SoundItem>> = _songsFlow.asStateFlow()

    fun fetchSongs() {
        val songsRef = database.getReference("songs")
        songsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val songList = mutableListOf<SoundItem>()
                for (child in snapshot.children) {
                    val song = child.getValue(SoundItem::class.java)
                    song?.let { songList.add(it) }
                }
                _songsFlow.value = songList
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("SongRepository", "Database error: ${error.message}")
            }
        })
    }
}
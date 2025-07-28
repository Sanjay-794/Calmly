package com.example.calmly.util

import android.content.Context
import com.example.calmly.media.MediaPlayerManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideMediaPlayerManager(
        @ApplicationContext context : Context
    ): MediaPlayerManager {
        return MediaPlayerManager(context)
    }
}
package com.example.calmly

import android.content.Context
import com.example.calmly.model.SoundItem
import com.example.calmly.screens.getRawAudioDurationInMinutes

object SoundData {

    fun getAllSounds(context: Context): List<SoundItem> {
        return listOf(
            SoundItem("Beautiful Dream", getRawAudioDurationInMinutes(context, R.raw.beautiful_dream).toString(), R.drawable.med1, R.raw.beautiful_dream),
            SoundItem("Chill Bro", getRawAudioDurationInMinutes(context, R.raw.chill_bro).toString(), R.drawable.med2, R.raw.chill_bro),
            SoundItem("Close The Lights", getRawAudioDurationInMinutes(context, R.raw.close_the_lights).toString(), R.drawable.med3, R.raw.close_the_lights),
            SoundItem("finding myself", getRawAudioDurationInMinutes(context, R.raw.finding_myself).toString(), R.drawable.med5, R.raw.finding_myself),
            SoundItem("harp relax", getRawAudioDurationInMinutes(context, R.raw.harp_relax).toString(), R.drawable.med6, R.raw.harp_relax),
            SoundItem("nature meditation", getRawAudioDurationInMinutes(context, R.raw.nature_meditation).toString(), R.drawable.med4, R.raw.nature_meditation),
            SoundItem("relax beat", getRawAudioDurationInMinutes(context, R.raw.relax_beat).toString(), R.drawable.sleep1, R.raw.relax_beat),
            SoundItem("serene view", getRawAudioDurationInMinutes(context, R.raw.serene_view).toString(), R.drawable.sleep2, R.raw.serene_view),
            SoundItem("spirit in woods", getRawAudioDurationInMinutes(context, R.raw.spirit_in_woods).toString(), R.drawable.sleep3, R.raw.spirit_in_woods),
            SoundItem("Don't Forget Me", getRawAudioDurationInMinutes(context, R.raw.dont_forget_me).toString(), R.drawable.sleep4, R.raw.dont_forget_me),
            SoundItem("Loving You Is Easy", getRawAudioDurationInMinutes(context, R.raw.loving_you_is_easy).toString(), R.drawable.sleep5, R.raw.loving_you_is_easy),
            SoundItem("Remember Me", getRawAudioDurationInMinutes(context, R.raw.remember_me).toString(), R.drawable.sleep6, R.raw.remember_me)
        )
    }

    fun getSleepSounds(context: Context): List<SoundItem> {
        return getAllSounds(context).takeLast(6)  // Last 3 for Sleep
    }

    fun getMeditationSounds(context: Context): List<SoundItem> {
        return getAllSounds(context).take(6)      // First 3 for Meditation
    }

    fun getSoundByResId(context: Context,resId: Int): SoundItem? {
        return getAllSounds(context).find { it.soundResId == resId }
    }
}
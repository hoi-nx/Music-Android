package com.sun.music_66.service

import androidx.annotation.IntDef

@IntDef(MediaPlayerShuffleType.OFF, MediaPlayerShuffleType.ON)
annotation class MediaPlayerShuffleType {
    companion object {
       const val OFF = 0
       const val ON = 1
    }
}

package com.sun.music_66.service

import androidx.annotation.IntDef

@IntDef(MediaPlayerStateType.PLAY, MediaPlayerStateType.PAUSE, MediaPlayerStateType.PREPARE)
annotation class MediaPlayerStateType {
    companion object {
        const val PLAY = 0
        const val PAUSE = 1
        const val PREPARE = 2
    }
}

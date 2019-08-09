package com.sun.music_66.service

import androidx.annotation.IntDef

@IntDef(MediaPlayerLoopType.NONE, MediaPlayerLoopType.ONE, MediaPlayerLoopType.ALL)
annotation class MediaPlayerLoopType {
    companion object {
        const val NONE = 0
        const val ONE = 1
        const val ALL = 2
    }
}

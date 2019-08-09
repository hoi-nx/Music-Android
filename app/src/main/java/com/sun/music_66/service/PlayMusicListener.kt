package com.sun.music_66.service

import com.sun.music_66.data.dto.TrackDto

interface PlayMusicListener {
    fun onPlayingStateListener(@MediaPlayerStateType state: Int)

    fun onTrackChangedListener(track: TrackDto)
}

package com.sun.music_66.base

import com.sun.music_66.data.dto.CollectionDto
import com.sun.music_66.data.dto.TrackDto
import java.text.FieldPosition

/**
 * Created by nguyenxuanhoi on 2019-07-24.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
interface BaseMediaPlayer {
    fun startTrack()

    fun addTracks(tracks: List<TrackDto>)

    fun addCurrentTrack(trackDto: TrackDto)

}
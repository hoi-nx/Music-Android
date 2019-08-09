package com.sun.music_66.service

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import com.sun.music_66.MusicApplication
import com.sun.music_66.data.dto.TrackDto
import com.sun.music_66.util.SongUtils
import com.sun.music_66.util.StringUtils
import java.io.IOException


/**
 * Created by nguyenxuanhoi on 2019-07-24.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class ManagerPlayMusic {

    @MediaPlayerStateType
    var state: Int = MediaPlayerStateType.PAUSE
    @MediaPlayerShuffleType
    var shuffleType = MediaPlayerShuffleType.OFF
    private var isCheckMusic = false

    @MediaPlayerLoopType
    var loopType = MediaPlayerLoopType.NONE

    private val mediaPlayer by lazy {
        MediaPlayer()
    }

    fun createMedia(trackDto: TrackDto, listener: HanlderPlayMusic, isCheckMusic: Boolean) {
        mediaPlayer.reset()
        setContentType()
        try {
            if (isCheckMusic) {
                mediaPlayer.setDataSource(
                        MusicApplication.applicationContext!!,
                        Uri.parse(StringUtils.generateStreamUrl(trackDto.id))
                )
                this.isCheckMusic = true
            } else {
                mediaPlayer.setDataSource(
                        MusicApplication.applicationContext!!,
                        Uri.fromFile(SongUtils.songFile(trackDto.id))
                )
                this.isCheckMusic = false
            }
            mediaPlayer.setOnErrorListener(listener)
            mediaPlayer.setOnCompletionListener(listener)
            mediaPlayer.setOnPreparedListener(listener)
            mediaPlayer.prepareAsync()
            state = MediaPlayerStateType.PREPARE
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun changeTrack(track: TrackDto, listener: HanlderPlayMusic) {
        createMedia(track, listener, isCheckMusic)
    }

    fun seekMusic(duration: Int) {
        mediaPlayer.seekTo(duration)
    }

    private fun setContentType() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val builder = AudioAttributes.Builder()
            builder.setUsage(AudioAttributes.USAGE_MEDIA)
            builder.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            val attributes = builder.build()
            mediaPlayer.setAudioAttributes(attributes)
        } else {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        }
    }

    fun startMusic() {
        mediaPlayer.start()
        state = MediaPlayerStateType.PLAY
    }

    fun getCurrentPositionMediaPlaying(): Int = if (mediaPlayer.isPlaying) mediaPlayer.currentPosition else 0

    fun getTotalDuration(): Int = mediaPlayer.duration

    fun pause() {
        state = MediaPlayerStateType.PAUSE
        mediaPlayer.pause()
    }

    fun stop() {
        state = MediaPlayerStateType.PAUSE
        mediaPlayer.stop()

    }

    fun release() {
        mediaPlayer.release()
    }

    companion object {
        @JvmStatic
        fun getInstance(): ManagerPlayMusic = ManagerPlayMusic()
    }


}
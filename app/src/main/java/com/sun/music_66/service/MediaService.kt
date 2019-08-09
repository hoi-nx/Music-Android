package com.sun.music_66.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.widget.Toast
import com.sun.music_66.MusicApplication
import com.sun.music_66.data.dto.GenreDto
import com.sun.music_66.data.dto.TrackDto
import com.sun.music_66.service.notification.PlayingNotificationImpl
import java.time.Duration
import java.util.*

/**
 * Created by nguyenxuanhoi on 2019-07-24.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class MediaService : Service(), HanlderPlayMusic {
    private var managerPlayMusic: ManagerPlayMusic? = null
    private var trackDtos = emptyList<TrackDto>()
    private lateinit var currentTrack: TrackDto

    fun setCurrentTrack(currentTrack: TrackDto) {
        this.currentTrack = currentTrack
    }

    fun getCurrentTrackDto() = if(::currentTrack.isInitialized) currentTrack else null

    private lateinit var binder: BinderService
    private lateinit var playMusicListener: PlayMusicListener
    fun setOnListenerMusic(playMusicListener: PlayMusicListener) {
        this.playMusicListener = playMusicListener
    }

    private val notification by lazy {
        PlayingNotificationImpl()
    }

    override fun onCreate() {
        super.onCreate()
        binder = BinderService()
        managerPlayMusic = ManagerPlayMusic.getInstance()
        notification.init(this)
    }

    override fun onBind(intent: Intent?): IBinder? = if (::binder.isInitialized) binder else null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (intent.action) {
                ACTION_PLAY -> {
                    playAndPause()
                }
                ACTION_NEXT -> {
                    nextTrack()
                }
                ACTION_PREVIOUS -> {
                    previous()
                }
                ACTION_QUIT -> {
                    notification.stop()
                    stopSelf()
                }
            }
        }
        return START_NOT_STICKY
    }

    fun playAndPause() {
        if (managerPlayMusic?.state == MediaPlayerStateType.PAUSE) {
            managerPlayMusic?.startMusic()
            notification.update(currentTrack, true)
            playMusicListener.onPlayingStateListener(MediaPlayerStateType.PLAY)
        } else {
            managerPlayMusic?.pause()
            notification.update(currentTrack, false)
            playMusicListener.onPlayingStateListener(MediaPlayerStateType.PAUSE)

        }
    }

    fun nextTrack() {
        if (managerPlayMusic?.shuffleType == MediaPlayerShuffleType.OFF) {
            currentTrack = getNextTrack()
            managerPlayMusic?.changeTrack(getNextTrack(), this)
            notification.update(getNextTrack(), true)
            addListener(getNextTrack())
        } else {
            currentTrack = getRandomTrack()
            managerPlayMusic?.changeTrack(getRandomTrack(), this)
            notification.update(getRandomTrack(), true)
            addListener(getRandomTrack())

        }
    }

    private fun getRandomTrack(): TrackDto {
        val random = Random()
        return trackDtos[random.nextInt(trackDtos.size)]
    }

    private fun getNextTrack(): TrackDto {
        val position = trackDtos.indexOf(currentTrack)
        return if (position == trackDtos.size - 1) {
            trackDtos[0]
        } else trackDtos[position + 1]
    }

    private fun getPreviousTrack(): TrackDto {
        val position = trackDtos.indexOf(currentTrack)
        return if (position == 0) {
            trackDtos[trackDtos.size - 1]
        } else trackDtos[position - 1]
    }

    fun previous() {
        currentTrack = getPreviousTrack()
        managerPlayMusic?.changeTrack(getPreviousTrack(), this)
        notification.update(getPreviousTrack(), true)
        addListener(getPreviousTrack())
    }

    private fun addListener(trackDto: TrackDto) {
        playMusicListener.onPlayingStateListener(MediaPlayerStateType.PLAY)
        playMusicListener.onTrackChangedListener(trackDto)
    }

    fun seekMusic(duration: Int) {
        managerPlayMusic?.seekMusic(duration)
    }

    override fun onPrepared(mp: MediaPlayer?) {
        managerPlayMusic?.startMusic()
    }

    override fun onCompletion(mp: MediaPlayer?) {
        // managerPlayMusic?.changeTrack(getNextTrack(), this)
        when (managerPlayMusic?.loopType) {
            MediaPlayerLoopType.ONE -> managerPlayMusic?.changeTrack(getCurrentTrack(), this)
            MediaPlayerLoopType.ALL -> nextTrack()
            else -> {
                if (isLastTracks(getCurrentTrack())) {
                    stopTrack()
                } else {
                    nextTrack()
                }
            }
        }
    }

    private fun stopTrack() {
        managerPlayMusic?.stop()
    }

    private fun isLastTracks(currentTrack: TrackDto): Boolean {
        return trackDtos.indexOf(currentTrack) == trackDtos.size - 1
    }

    private fun getCurrentTrack(): TrackDto = currentTrack

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        return true
    }

    fun playMusic(trackDto: TrackDto, isCheckMusic: Boolean) {
        managerPlayMusic?.createMedia(trackDto, this, isCheckMusic)
        notification.update(trackDto, true)
    }

    fun addTracks(trackDto: List<TrackDto>) {
        this.trackDtos = trackDto
    }

    fun getCurrentDuration(): Int = managerPlayMusic?.getCurrentPositionMediaPlaying() ?: 0

    fun getTotalDuration(): Int = managerPlayMusic?.getTotalDuration() ?: 0

    fun getShuffleType(): Int = managerPlayMusic?.shuffleType!!

    fun setShuffleType(shuffle: Int) {
        managerPlayMusic?.shuffleType = shuffle
    }

    fun getManager(): ManagerPlayMusic? = managerPlayMusic

    inner class BinderService : Binder() {
        fun getService(): MediaService {
            return this@MediaService
        }
    }
    companion object {
        private val PACKAGE_NAME = MusicApplication.applicationContext?.packageName
        val ACTION_PLAY = "$PACKAGE_NAME.play"
        val ACTION_PREVIOUS = "$PACKAGE_NAME.previous"
        val ACTION_NEXT = "$PACKAGE_NAME.next"
        val ACTION_QUIT = "$PACKAGE_NAME.quitservice"
    }

}


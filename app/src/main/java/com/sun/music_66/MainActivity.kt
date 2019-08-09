package com.sun.music_66

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.view.MenuItem
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sun.music_66.base.view.BaseActivity
import com.sun.music_66.constant.Constants
import com.sun.music_66.constant.CurrentItem
import com.sun.music_66.data.dto.TrackDto
import com.sun.music_66.service.*
import com.sun.music_66.util.LoadImage
import com.sun.music_66.util.StringUtils
import com.sun.music_66.view.adapter.ViewpagerFragmentAdapter
import com.sun.music_66.view.genres.GenreFragment
import kotlinx.android.synthetic.main.activity_home.*


class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener,
    ViewPager.OnPageChangeListener, PlayMusicListener, View.OnClickListener, SeekBar.OnSeekBarChangeListener,
    MotionLayout.TransitionListener {
    override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {

    }

    override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
    }

    override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
    }

    override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
    }

    private var state = 0
    private lateinit var connection: ServiceConnection
    private lateinit var serviceMedia: MediaService
    private lateinit var adapter: ViewpagerFragmentAdapter

    override fun getContentViewId(): Int = R.layout.activity_home

    override fun initializeData(savedInstanceState: Bundle?) {

    }

    override fun initializeComponents() {
        initBackground()
        adapter = ViewpagerFragmentAdapter(supportFragmentManager).apply {
            view_pager.adapter = this
        }

        // ContextCompat.startForegroundService(this, )
        startService(getIntent(this))
        connectServiceMediaToRemote()

    }

    override fun registerListeners() {
        bottom_nav.setOnNavigationItemSelectedListener(this)
        view_pager.addOnPageChangeListener(this)
        image_play.setOnClickListener(this)
        prev_button.setOnClickListener(this)
        next_button.setOnClickListener(this)
        seek_bar.setOnSeekBarChangeListener(this)
        imageShuffle.setOnClickListener(this)
        imageLoop.setOnClickListener(this)
        imageFavorite.setOnClickListener(this)
        motion_layout.setTransitionListener(this)

    }

    override fun unregisterListeners() {
        super.unregisterListeners()
        if (::connection.isInitialized) unbindService(connection)

    }

    override fun onClick(v: View) {
        if (!::serviceMedia.isInitialized) {
            return
        }
        when (v.id) {
            R.id.image_play -> {
                serviceMedia.playAndPause()
            }
            R.id.prev_button -> {
                serviceMedia.previous()
            }
            R.id.next_button -> {
                serviceMedia.nextTrack()

            }
            R.id.imageShuffle -> {
                handleShuffle()

            }
            R.id.imageLoop -> {
                handleLoop()
            }
            R.id.imageFavorite -> {

            }
        }
    }

    private fun handleShuffle() {
        when (serviceMedia.getShuffleType()) {
            MediaPlayerShuffleType.OFF -> serviceMedia.setShuffleType(MediaPlayerShuffleType.ON)
            MediaPlayerShuffleType.ON -> serviceMedia.setShuffleType(MediaPlayerShuffleType.OFF)
            else -> {
            }
        }
        updateShuffleMusicSetting()
    }

    private fun updateShuffleMusicSetting() {
        if (serviceMedia.getShuffleType() == MediaPlayerShuffleType.OFF) {
            imageShuffle.setImageResource(R.drawable.ic_not_shuffle)
        } else {
            imageShuffle.setImageResource(R.drawable.ic_shuffle)
        }
    }

    private fun updateLoopMusicSetting() {
        when {
            serviceMedia.getManager()?.loopType == MediaPlayerLoopType.NONE -> imageLoop.setImageResource(R.drawable.ic_not_loop)
            serviceMedia.getManager()?.loopType == MediaPlayerLoopType.ONE -> imageLoop.setImageResource(R.drawable.ic_loop_one)
            else -> imageLoop.setImageResource(R.drawable.ic_loop_all)
        }
    }

    private fun handleLoop() {
        when (serviceMedia.getManager()?.loopType) {
            MediaPlayerLoopType.NONE -> serviceMedia.getManager()?.loopType = MediaPlayerLoopType.ALL
            MediaPlayerLoopType.ALL -> serviceMedia.getManager()?.loopType = MediaPlayerLoopType.ONE
            MediaPlayerLoopType.ONE -> serviceMedia.getManager()?.loopType = MediaPlayerLoopType.NONE
            else -> {
            }
        }
        updateLoopMusicSetting()
    }


    private fun connectServiceMediaToRemote() {
        connection = object : ServiceConnection {
            override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
                val binder = iBinder as MediaService.BinderService
                serviceMedia = binder.getService()
                serviceMedia.setOnListenerMusic(this@MainActivity)
                serviceMedia.getCurrentTrackDto()?.let {
                    updateMiniPlayMusic(it)
                }

            }

            override fun onServiceDisconnected(componentName: ComponentName) {
                Toast.makeText(this@MainActivity, getString(R.string.error_connect_service), Toast.LENGTH_LONG).show()

            }
        }
        bindService(getIntent(this), connection, BIND_AUTO_CREATE)
    }

    private fun initBackground() {
        val wallpaperManager = WallpaperManager.getInstance(this)
        val wallpaperDrawable = wallpaperManager.drawable
        Glide.with(this).load(wallpaperDrawable).into(object : SimpleTarget<Drawable>() {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                motion_layout.background = resource

            }
        })
        seek_bar.progressDrawable.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN)
        seek_bar.thumb.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN)
    }


    override fun onBackPressed() {
        if (view_pager.currentItem != 0) {
            view_pager.currentItem = 0
            return
        }
        if (view_pager.currentItem == 0) {
            if ((adapter.getItem(view_pager.currentItem) as GenreFragment).popStack()) return
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_genre -> {
                setCurrentViewpager(item, CurrentItem.ZERO)
            }
            R.id.navigation_library -> {
                setCurrentViewpager(item, CurrentItem.ONE)
            }
            R.id.navigation_setting -> {
                setCurrentViewpager(item, CurrentItem.TWO)
            }
        }
        return false
    }

    private fun setCurrentViewpager(item: MenuItem, position: Int) {
        view_pager.currentItem = position
        item.isChecked = true
    }

    @SuppressLint("SetTextI18n")
    fun updateMiniPlayMusic(track: TrackDto) {
        // textTrack.isSelected = true
        imageFavorite.setImageResource(R.drawable.ic_favorite_border_white_24dp)
        next_button.setImageResource(R.drawable.ic_skip_next)
        imageLoop.setImageResource(R.drawable.ic_not_loop)
        imageShuffle.setImageResource(R.drawable.ic_not_shuffle)
        prev_button.setImageResource(R.drawable.ic_skip_previous)
        text_time.setBackgroundResource(R.drawable.time_text_view_background)
        view_bottom_gradient.setBackgroundResource(R.drawable.now_playing_bottom_gradient)

        textTrack.text = track.title
        textArtist.text = track.description
        text_time.text =
            StringUtils.convertTime(serviceMedia.getCurrentDuration()) + " | " + StringUtils.convertTime(track.fullDuration)
        LoadImage.loadImage(imageTrack, track.artWorkUrl)
        image_play.setImageResource(R.drawable.ic_play_music)
        initHandlerSyncTime(track)


    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            val trackDto = it.getParcelableExtra(Constants.ARGUMENT_TRACK) as? TrackDto
            trackDto?.let { track ->
                updateMiniPlayMusic(track)
            }
        }

    }

    override fun onPlayingStateListener(state: Int) {
        this.state = state
        if (state == MediaPlayerStateType.PAUSE) {
            image_play.setImageResource(R.drawable.ic_notify_play)
        } else {
            image_play.setImageResource(R.drawable.ic_play_music)
        }

    }

    override fun onTrackChangedListener(track: TrackDto) {
        updateMiniPlayMusic(track)
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageSelected(position: Int) {
        bottom_nav.menu.getItem(position).isChecked = true
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    private fun initHandlerSyncTime(track: TrackDto) {
        seek_bar.max = track.fullDuration / 1000
        val mHandlerSyncTime = Handler()
        mHandlerSyncTime.postDelayed(object : Runnable {
            @SuppressLint("SetTextI18n")
            override fun run() {
                if (state == MediaPlayerStateType.PLAY) {
                    val currentTime = serviceMedia.getCurrentDuration() / 1000
                    seek_bar.progress = currentTime
                    text_time.text =
                        StringUtils.convertTime(serviceMedia.getCurrentDuration()) +
                                " | " +
                                StringUtils.convertTime(serviceMedia.getTotalDuration())
                }
                mHandlerSyncTime.postDelayed(this, 1000)
            }
        }, 1000)
    }


    fun getService(): MediaService? = if (::serviceMedia.isInitialized) serviceMedia else null


    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (fromUser) {
            serviceMedia.seekMusic(progress * 1000)
        }

    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }


    companion object {
        @JvmStatic
        fun getIntent(context: Context): Intent = Intent(context, MediaService::class.java)
    }


}

package com.sun.music_66.service

import android.media.MediaPlayer

/**
 * Created by nguyenxuanhoi on 2019-07-24.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
interface HanlderPlayMusic: MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener,
    MediaPlayer.OnPreparedListener

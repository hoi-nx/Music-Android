package com.sun.music_66.util

import android.content.Context
import android.net.ConnectivityManager
import com.sun.music_66.MusicApplication
/**
 * Created by nguyenxuanhoi on 2019-07-17.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
object NetworkUtil {
    fun isNetworkAvailable(): Boolean {
        val manager = MusicApplication.applicationContext?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return manager.activeNetworkInfo?.isConnected ?: false
    }
}

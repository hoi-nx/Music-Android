package com.sun.music_66.data.remote

import com.sun.music_66.MusicApplication
import com.sun.music_66.R
import com.sun.music_66.exceptions.NetworkException
import com.sun.music_66.util.NetworkUtil
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by nguyenxuanhoi on 2019-07-17.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class NetworkInterceptor : Interceptor {
    @Throws(NetworkException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!NetworkUtil.isNetworkAvailable()) {
            throw NetworkException(MusicApplication.applicationContext!!.getString(R.string.error_connect))
        }
        return chain.proceed(chain.request())
    }
}

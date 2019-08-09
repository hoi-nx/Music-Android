package com.sun.music_66.util

import com.sun.music_66.BuildConfig
import com.sun.music_66.MusicApplication
import com.sun.music_66.MusicApplication.Companion.applicationContext
import com.sun.music_66.data.remote.CacheInterceptor
import com.sun.music_66.data.remote.NetworkInterceptor
import com.sun.music_66.data.remote.ResponseInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by nguyenxuanhoi on 2019-07-17.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
object RequestAPI {
    private const val TIME_OUT = 10L

    private fun logInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    private fun createOkHttpClient(): OkHttpClient {
        val httpCacheDirectory = File(applicationContext?.cacheDir, "http-cache")
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        val cache = Cache(httpCacheDirectory, cacheSize.toLong())
        val builder = OkHttpClient.Builder()
            .addInterceptor(logInterceptor())
            .addInterceptor(NetworkInterceptor())
            .addNetworkInterceptor(CacheInterceptor())
            .addInterceptor(ResponseInterceptor())
            .cache(cache)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        return builder.build()
    }

    @Throws(IOException::class)
    fun receiver(url: String): String {
        val request = Request.Builder()
            .url(url)
            .build()
        createOkHttpClient().newCall(request).execute().use { response -> return response.body()!!.string() }
    }
}

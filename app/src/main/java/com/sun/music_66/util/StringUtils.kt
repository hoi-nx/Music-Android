package com.sun.music_66.util

import android.annotation.SuppressLint
import com.sun.music_66.BuildConfig
import com.sun.music_66.MusicApplication
import com.sun.music_66.constant.Constants
import java.io.InputStream
import java.text.SimpleDateFormat

/**
 * Created by nguyenxuanhoi on 2019-07-15.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
object StringUtils {
    fun generateGenreUrl(kind: String, genre: String, limit: Int, offset: Int): String =
        String.format(Constants.BASE_GENRE_URL, kind, genre, BuildConfig.CLIENT_ID, limit, offset)

    fun generateTredingUrl(kind: String, genre: String): String =
            String.format(Constants.BASE_TRENDING_URL, kind, genre, BuildConfig.CLIENT_ID)

    fun reformatImageUrl(url: String): String {
        return url.replace(Constants.IMAGE_LARGE, Constants.IMAGE_FULL)
    }
    @SuppressLint("SimpleDateFormat")
    fun convertTime(time:Int):String{
        val format = SimpleDateFormat("mm:ss")
        return format.format(time)

    }

    fun generateSearchUrl(query: String, limit: Int, offset: Int): String =
        String.format(Constants.BASE_SEARCH_URL, query, BuildConfig.CLIENT_ID, limit, offset)

    fun generateDownloadUrl(trackId: Long): String =
        String.format(Constants.BASE_DOWNLOAD_URL, trackId, BuildConfig.CLIENT_ID)

    fun generateStreamUrl(trackId: Int): String {
        return String.format(Constants.BASE_STREAM_URL, trackId, BuildConfig.CLIENT_ID)
    }
    fun readJSONFromAsset(file: String): String? =
        try {
            val inputStream: InputStream = MusicApplication.applicationContext?.assets!!.open(file)
            val json = inputStream.bufferedReader().use { it.readText() }
            json
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }

}

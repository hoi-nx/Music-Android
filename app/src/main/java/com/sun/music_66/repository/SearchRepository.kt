package com.sun.music_66.repository

import android.annotation.SuppressLint
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.sun.music_66.base.BaseAsyncTask
import com.sun.music_66.data.dto.MusicDto
import com.sun.music_66.data.dto.TrackDto
import com.sun.music_66.util.RequestAPI
import com.sun.music_66.util.TrackEntity
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by nguyenxuanhoi on 2019-07-30.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class SearchRepository {
    private val musics: MutableList<TrackDto> = mutableListOf()
    private lateinit var onSuccess: (result: List<TrackDto>) -> Unit
    fun setOnSuccess(onSuccess: (result: List<TrackDto>) -> Unit) {
        this.onSuccess = onSuccess
    }

    private lateinit var onFail: (t: Throwable) -> Unit
    fun setOnFail(onFail: (result: Throwable) -> Unit) {
        this.onFail = onFail
    }

    @SuppressLint("StaticFieldLeak")
    fun searchMusic(url: String) {

        object : BaseAsyncTask<String, Void, List<TrackDto>>() {
            override fun onBackground(vararg params: String): List<TrackDto> {
                val tracks: List<TrackDto> = ArrayList()
                val json = RequestAPI.receiver(params[0])
                val jsonArray = JSONArray(json)
                for(i in 0 until jsonArray.length()){
                    val jsonObject= jsonArray.getJSONObject(i)
                    val trackDto=TrackDto(jsonObject.getInt(TrackEntity.ID),jsonObject.getInt(TrackEntity.DURATION),
                            jsonObject.getString(TrackEntity.DESCRIPTION),
                            jsonObject.getString(TrackEntity.TITLE),
                            jsonObject.getString(TrackEntity.ARTWORK_URL),
                            null,
                            jsonObject.getBoolean(TrackEntity.STREAMABLE),
                            jsonObject.getString(TrackEntity.DOWNLOAD_URL),
                            jsonObject.getInt(TrackEntity.LIKES_COUNT),
                            null,
                            jsonObject.getBoolean(TrackEntity.DOWNLOADABLE))
                    (tracks as ArrayList).add(trackDto)
                }
                Log.d("TEST",tracks.size.toString())

                return tracks
            }

            override fun onResult(result: List<TrackDto>) {
                if (::onSuccess.isInitialized) {
                    onSuccess.invoke(result)
                }

            }

            override fun onFailure(result: Throwable) {
                if (::onFail.isInitialized) {
                    onFail.invoke(result)
                }
            }

        }.execute(url)
    }

}
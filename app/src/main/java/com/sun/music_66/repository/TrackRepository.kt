package com.sun.music_66.repository

import android.annotation.SuppressLint
import android.content.ContentValues
import android.util.Log
import com.google.gson.Gson
import com.sun.music_66.MusicApplication
import com.sun.music_66.base.BaseAsyncTask
import com.sun.music_66.data.dto.GenreDto
import com.sun.music_66.data.dto.MusicDto
import com.sun.music_66.data.dto.TrackDto
import com.sun.music_66.data.local.DatabaseMusic
import com.sun.music_66.data.local.MusicDevice
import com.sun.music_66.data.local.MusicDownload
import com.sun.music_66.util.GenresEntity
import com.sun.music_66.util.RequestAPI
import com.sun.music_66.util.StringUtils
import com.sun.music_66.util.TrackEntity
import org.json.JSONArray

/**
 * Created by nguyenxuanhoi on 2019-07-19.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class TrackRepository {
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
    fun getListTrackTrending(url: String) {
        object : BaseAsyncTask<String, Void, List<TrackDto>>() {
            override fun onBackground(vararg params: String): List<TrackDto> {
                val tracks: List<TrackDto> = ArrayList()
                val json = RequestAPI.receiver(params[0])
                val trendingDto = Gson().fromJson(json, MusicDto::class.java)
                for (i in 0 until trendingDto.collection.size) {
                    (tracks as ArrayList).add(trendingDto.collection[i].trackDto)
                }
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

    fun getListGenres(): ArrayList<GenreDto> {
        val listGenre = ArrayList<GenreDto>()
        val json = StringUtils.readJSONFromAsset("genres.json")
        json?.let {
            val jsonArray = JSONArray(it)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val genreDto = GenreDto(
                    jsonObject.getString(GenresEntity.ID),
                    jsonObject.getString(GenresEntity.NAME),
                    jsonObject.getString(GenresEntity.IMAGE)
                )
                listGenre.add(genreDto)
            }
        }
        return listGenre
    }

     fun insertTrack(trackDto: TrackDto): Boolean {
        val db = MusicApplication.dataHepler?.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(TrackEntity.ID, trackDto.id)
        contentValues.put(TrackEntity.TITLE, trackDto.title)
        contentValues.put(TrackEntity.ARTWORK_URL, trackDto.artWorkUrl)
        contentValues.put(TrackEntity.DESCRIPTION, trackDto.description)
        contentValues.put(TrackEntity.DURATION, trackDto.fullDuration)
         db?.let {
             val result = it.insert(DatabaseMusic.DatabaseGenres.TABLE_DOWNLOAD, null, contentValues)
             return result > 0
         }

         return false

    }

    fun getMusicDownload():List<TrackDto>{
        val db = MusicApplication.dataHepler?.readableDatabase
        val sql = "SELECT * FROM Download"
        db?.let {
            val cursor = it.rawQuery(sql, null)
            val indexId = cursor.getColumnIndex(TrackEntity.ID) //0
            val indexTitle = cursor.getColumnIndex(TrackEntity.TITLE) //1
            val indexDuration = cursor.getColumnIndex(TrackEntity.DURATION) //1
            val indexDescription = cursor.getColumnIndex(TrackEntity.DESCRIPTION) //1
            val indexUrl = cursor.getColumnIndex(TrackEntity.ARTWORK_URL) //1

            while (cursor.moveToNext()) {
                val id = cursor.getInt(indexId)
                val title = cursor.getString(indexTitle)
                val duration = cursor.getInt(indexDuration)
                val description = cursor.getString(indexDescription)
                val url = cursor.getString(indexUrl)
                musics.add(TrackDto(id,duration,description,title,url,null,null,null,null,null,null))
            }
        }
        return musics
    }


}
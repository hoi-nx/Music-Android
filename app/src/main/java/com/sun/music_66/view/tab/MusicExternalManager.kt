package com.sun.music_66.view.tab

import android.content.Context
import android.provider.MediaStore

import com.sun.music_66.data.local.MusicDevice

import java.util.ArrayList
import java.util.Date

/**
 * Created by ducnd on 7/22/17.
 */

class MusicExternalManager(context: Context) {
    private val externals: MutableList<MusicDevice>
    init {
        externals = ArrayList()
        queyAllMusic(context)
    }

    private fun queyAllMusic(context: Context) {
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf("_id",
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ARTIST_ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATE_MODIFIED)
        val cursor = context.contentResolver
                .query(uri, projection, null, null, null) ?: return
        val indexPath = cursor.getColumnIndex(MediaStore.Audio.Media.DATA)
        val indexName = cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)
        val indexDuration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
        val indexArtist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
        val indexDateModifier = cursor.getColumnIndex(MediaStore.Audio.Media.DATE_MODIFIED)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val path = cursor.getString(indexPath)
            val name = cursor.getString(indexName)
            val duration = cursor.getLong(indexDuration)
            val artist = cursor.getString(indexArtist)
            val dateModify = cursor.getLong(indexDateModifier)
            externals.add(MusicDevice(name, artist, duration, Date(dateModify * 1000), path))
            cursor.moveToNext()
        }
        cursor.close()
    }

    fun getExternals(): List<MusicDevice> {
        return externals
    }

    companion object {
        private val TAG = MusicExternalManager::class.java.simpleName
    }
}

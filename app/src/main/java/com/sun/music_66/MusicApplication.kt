package com.sun.music_66

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.sun.music_66.constant.Constants
import com.sun.music_66.data.local.DatabaseHelper
import com.sun.music_66.data.local.DatabaseMusic

class MusicApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        Log.d("MusicApplication", DatabaseMusic.DatabaseGenres.SQL_CREATE_GENRES)
        DATA = DatabaseHelper(this, Constants.DATABASE_NAME, Constants.DATA_VERSION)
    }

    companion object {
        lateinit var DATA: DatabaseHelper
        val dataHepler: DatabaseHelper?
            get() = if (::DATA.isInitialized) DATA else null
        lateinit var INSTANCE: MusicApplication
            private set
        val applicationContext: Context?
            get() = if (::INSTANCE.isInitialized) INSTANCE.applicationContext else null
    }
}

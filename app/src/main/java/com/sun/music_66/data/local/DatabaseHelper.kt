package com.sun.music_66.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/**
 * Created by nguyenxuanhoi on 2019-07-21.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class DatabaseHelper(context: Context, dataName: String, dataVersion: Int) : SQLiteOpenHelper(context,dataName,null,dataVersion) {

    override fun onCreate(db: SQLiteDatabase?) {
        Log.d("DatabaseHelper",DatabaseMusic.DatabaseGenres.SQL_CREATE_GENRES)
        db?.execSQL(DatabaseMusic.DatabaseGenres.SQL_CREATE_GENRES)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}
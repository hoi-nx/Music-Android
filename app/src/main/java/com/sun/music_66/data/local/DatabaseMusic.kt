package com.sun.music_66.data.local

import com.sun.music_66.util.GenresEntity.Companion.IMAGE
import com.sun.music_66.util.TrackEntity

/**
 * Created by nguyenxuanhoi on 2019-07-21.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
object DatabaseMusic {
    const val CREATE_TABLE = "create table if not exists "
    const val ID = "id"
    const val TEXT = " TEXT, "
    const val INTEGER_UNIQUE = " INTEGER UNIQUE, "
    const val INTEGER = " INTEGER, "
    const val PRIMARY_KEY = "PRIMARY KEY("
    const val UNIQUE = "unique"

    class DatabaseGenres {
        companion object {
            const val TABLE_DOWNLOAD = "Download"
            var SQL_CREATE_GENRES =
                "$CREATE_TABLE $TABLE_DOWNLOAD($ID$INTEGER_UNIQUE${TrackEntity.TITLE}$TEXT${TrackEntity.DURATION}" +
                        "$INTEGER${TrackEntity.DESCRIPTION}$TEXT${TrackEntity.ARTWORK_URL}$TEXT$PRIMARY_KEY$ID))"
        }
    }
}
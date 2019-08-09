package com.sun.music_66.util

import androidx.annotation.StringDef

/**
 * Created by nguyenxuanhoi on 2019-07-18.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
@StringDef(PublisherEntity.ID, PublisherEntity.ARTIST, PublisherEntity.ALBUM_TITLE)
annotation class PublisherEntity {
    companion object {
        const val ID = "id"
        const val ARTIST = "artist"
        const val ALBUM_TITLE = "album_title"
    }
}

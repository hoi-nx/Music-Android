package com.sun.music_66.util

import androidx.annotation.StringDef

/**
 * Created by nguyenxuanhoi on 2019-07-21.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
@StringDef(GenresEntity.ID, GenresEntity.NAME, GenresEntity.IMAGE)
annotation class GenresEntity {
    companion object {
        const val ID = "id"
        const val NAME = "name"
        const val IMAGE = "image_url"
    }
}
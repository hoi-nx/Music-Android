package com.sun.music_66.data.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by nguyenxuanhoi on 2019-07-19.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
data class MusicDto(@SerializedName("collection") val collection: List<CollectionDto>)

data class CollectionDto(@SerializedName("track") val trackDto: TrackDto)
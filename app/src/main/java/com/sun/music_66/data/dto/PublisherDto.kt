package com.sun.music_66.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sun.music_66.util.PublisherEntity
import kotlinx.android.parcel.Parcelize

/**
 * Created by nguyenxuanhoi on 2019-07-16.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
@Parcelize
data class PublisherDto(@SerializedName(PublisherEntity.ID) val id: Int?,
                        @SerializedName(PublisherEntity.ARTIST) val artist: String?,
                        @SerializedName(PublisherEntity.ALBUM_TITLE) val albumTitle: String?) : Parcelable


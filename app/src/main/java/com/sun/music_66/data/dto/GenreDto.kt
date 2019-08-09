package com.sun.music_66.data.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by nguyenxuanhoi on 2019-07-16.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
@Parcelize
data class GenreDto(val id:String,val nameGenre:String,val image:String) : Parcelable

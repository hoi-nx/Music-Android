package com.sun.music_66.view.tab

import com.sun.music_66.base.OnDataLoadedCallback
import com.sun.music_66.data.dto.GenreDto
import com.sun.music_66.data.dto.TrackDto
import com.sun.music_66.data.local.MusicDevice

/**
 * Created by nguyenxuanhoi on 2019-07-29.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
interface MySongContract{
    interface View

    interface Presenter {
        fun getAllMusic():List<MusicDevice>
    }
}
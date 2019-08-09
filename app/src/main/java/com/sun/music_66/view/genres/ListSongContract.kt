package com.sun.music_66.view.genres

import com.sun.music_66.base.OnDataLoadedCallback
import com.sun.music_66.data.dto.CollectionDto
import com.sun.music_66.data.dto.TrackDto

/**
 * Created by nguyenxuanhoi on 2019-07-22.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
interface ListSongContract {
    interface View : OnDataLoadedCallback<List<TrackDto>>
    interface Presenter{
        fun getMusicByGenres(genres:String)
    }
}
package com.sun.music_66.view.tab

import com.sun.music_66.data.dto.TrackDto

/**
 * Created by nguyenxuanhoi on 2019-07-29.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
interface DownloadContract{
    interface View

    interface Presenter {
        fun saveDownload(trackDto: TrackDto):Boolean
        fun getMusicDownload():List<TrackDto>
    }
}
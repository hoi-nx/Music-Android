package com.sun.music_66.view.tab

import com.sun.music_66.base.view.BasePresenter
import com.sun.music_66.data.dto.TrackDto
import com.sun.music_66.data.local.MusicDownload
import com.sun.music_66.repository.TrackRepository

/**
 * Created by nguyenxuanhoi on 2019-07-29.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class DownloadPresenter :BasePresenter<DownloadContract.View>(), DownloadContract.Presenter {

    private val trackRepository by lazy {
        TrackRepository()
    }
    override fun getMusicDownload(): List<TrackDto> =trackRepository.getMusicDownload()

    override fun saveDownload(trackDto: TrackDto) = trackRepository.insertTrack(trackDto)

}
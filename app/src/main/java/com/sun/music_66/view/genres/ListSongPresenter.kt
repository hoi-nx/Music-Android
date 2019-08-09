package com.sun.music_66.view.genres

import com.sun.music_66.base.view.BasePresenter
import com.sun.music_66.repository.TrackRepository
import com.sun.music_66.util.StringUtils

/**
 * Created by nguyenxuanhoi on 2019-07-22.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class ListSongPresenter: BasePresenter<ListSongContract.View>(),ListSongContract.Presenter {
    private val responsitory by lazy {
        TrackRepository()
    }

    override fun getMusicByGenres(genres:String) {
        responsitory.getListTrackTrending(StringUtils.generateGenreUrl("top",genres,20,20))
        responsitory.setOnSuccess {
            getView()?.onSuccess(it)
        }
        responsitory.setOnFail {
            getView()?.onFailed(it)
        }
    }
}